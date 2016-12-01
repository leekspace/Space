package com.leekli.demo.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {
	final static AtomicInteger ai = new AtomicInteger(0);

	public static void main(String[] args) {
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS, 
				new ArrayBlockingQueue<Runnable>(2),
				new ThreadPoolExecutor.CallerRunsPolicy());
		
		
		for(int i=0;i<100;i++){
			System.out.println(i);
			final int n = i;
			executor.submit(new Runnable() {
				@Override
				public void run() {
					System.out.println("handle:"+n);
					ai.set(ai.get()+1);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			});
			System.out.println("---" + ai.get());
		}

		
	}
}

