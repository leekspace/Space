package com.leekli.demo.metrics;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

public class Test {

	private static final MetricRegistry metrics = new MetricRegistry();
	static OSMonitor  os = OSMonitor.getInstance();
	/**
	 * 在控制台上打印输出
	 */
	private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

	public static void main(String[] args) {
        reporter.start(3, TimeUnit.SECONDS);


        //注册到容器中
        metrics.register(MetricRegistry.name(TestGauges.class, "os", "cpuidle"), new Gauge<Integer>(){ //实例化一个Gauge
        	 @Override
             public Integer getValue() {
                 return os.getSystemCpuIdle();
             }
        });
        
        metrics.register(MetricRegistry.name(TestGauges.class, "os", "heapMemoryUsed"), new Gauge<Long>(){ //实例化一个Gauge

			@Override
			public Long getValue() {
				return os.getHeapMemoryUsed();
			}
        });
        
        //主进行等待
        try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
