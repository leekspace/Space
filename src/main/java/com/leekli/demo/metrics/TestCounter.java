package com.leekli.demo.metrics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.core.task.TaskExecutor;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

/**
 * 
 * Counter是Gauge的一个特例，维护一个计数器，可以通过inc()和dec()方法对计数器做修改
 * 
 * @author liwei
 * @Date   2016年11月28日 上午11:23:55 
 * @Desc
 */
public class TestCounter {
	/**
	 * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
	 */
	private static final MetricRegistry metrics = new MetricRegistry();
	
	/**
	 * 在控制台上打印输出
	 */
	private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();
	
	
	/**
	 * 实例化一个counter
	 */
	private static Counter pendingJobs = metrics.counter(MetricRegistry.name(TestCounter.class, "pedding-jobs"));//参数仅用于描述
	
	private static Queue<String> queue = new LinkedList<String>();
	
	
	
	 public static void add(String str) {
	        pendingJobs.inc();
	        queue.offer(str);
	    }
	 
	 public static String take() {
	        pendingJobs.dec();
	        return queue.poll();
	    }
	 
	 public static void main(String[]args) throws InterruptedException {
	        reporter.start(3, TimeUnit.SECONDS);//每3秒报告一次
	        while(true){
	            add("1");
	            if(RandomUtils.nextInt()%10 ==0){
	            	take();
	            }
	            	
	            Thread.sleep(100);
	        }

	    }
	
	
}

