package com.py4jdemo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;



import py4j.GatewayServer;
import py4j.GatewayServer.GatewayServerBuilder;
/**
 * py call java
 * @author liwei
 * @Date   2017年2月24日 下午1:32:52 
 * @Desc
 */
public class Py4jServer  implements ApplicationListener<ApplicationContextEvent>{

    @Autowired
    Endpoint endpoint;
    
    @Value("${py4j_server_port}")
    private int port;
    
    @Override
    public void onApplicationEvent(ApplicationContextEvent arg0) {
        
        GatewayServerBuilder build = new GatewayServer.GatewayServerBuilder(endpoint);
 
        InetAddress javaAddress = null;
        try {
            javaAddress = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException e) {
            javaAddress = GatewayServer.defaultAddress();
        }
        
        GatewayServer server = build.javaPort(port)
                                    .javaAddress(javaAddress)
                                    .build();
//        GatewayServer server = new GatewayServer(endpoint,port);
        server.start();                   
    }
   public static void main(String[] args) {
 
}

}

