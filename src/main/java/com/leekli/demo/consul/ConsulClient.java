package com.leekli.demo.consul;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.io.BaseEncoding;
import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.async.ConsulResponseCallback;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.orbitz.consul.model.health.ServiceHealth;
import com.orbitz.consul.model.kv.Value;
import com.orbitz.consul.option.QueryOptions;


public class ConsulClient {
	private static Consul consul ;
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsulClient.class);
	
	static{
		consul = Consul.builder().withHostAndPort(HostAndPort.fromHost("10.100.100.84").withDefaultPort(8500))
				//.withHostAndPort(HostAndPort.fromHost("10.100.100.241").withDefaultPort(8500))
				.build();
		 
	}
	public static void main(String[] args) {
//		registAndSendPass();
//		findAvailableServices();
		blockCallValue();
	}

	
	public static void blockCallValue(){

		final KeyValueClient kvClient = consul.keyValueClient();

		kvClient.putValue("foo", "bar");

		ConsulResponseCallback<Optional<Value>> callback = new ConsulResponseCallback<Optional<Value>>() {

		    AtomicReference<BigInteger> index = new AtomicReference<BigInteger>(null);
		   
		    
		    @Override
		    public void onComplete(ConsulResponse<Optional<Value>> consulResponse) {

		        if (consulResponse.getResponse().isPresent()) {
		            Value v = consulResponse.getResponse().get();
		            LOGGER.info("Value is: {}", new String(BaseEncoding.base64().decode(v.getValue().toString())));
		        }
		        index.set(consulResponse.getIndex());
		        watch();
		    }

		    void watch() {
		        kvClient.getValue("foo", QueryOptions.blockMinutes(5, index.get()).build(), this);
		    }

		    @Override
		    public void onFailure(Throwable throwable) {
		            System.out.println(throwable.getMessage());
		            watch();
		        }
		    };

		    kvClient.getValue("foo", QueryOptions.blockMinutes(5, new BigInteger("0")).build(), callback);
	}
	
	
	public static void findAvailableServices(){
		HealthClient healthClient = consul.healthClient();
		List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("MyService").getResponse();
		System.out.println(nodes);
		for(ServiceHealth sh : nodes){
			System.out.println("============");
			System.out.println(sh.getService().toString());
			System.out.println(sh.getNode().toString());
		}
	}
	public static void registAndSendPass() {
		
		AgentClient agentClient = consul.agentClient();


		String serviceId = "MyService";
		//agentClient.register(8080, 3L, serviceName, serviceId); // registers
		QueryOptions options = QueryOptions.blockSeconds(5, BigInteger.valueOf(2)).build();
		
		Registration registration = ImmutableRegistration.builder()
					.address("3.3.3.3")
					.port(1000)
					.name("MyService")
					.addTags("tag")
					.id(serviceId)
					.check(Registration.RegCheck.ttl(10))
					.build();
					
		agentClient.register(registration, options);											// with a TTL of
																// 3 seconds
		try {
			
			agentClient.pass(serviceId);
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
	}

}
