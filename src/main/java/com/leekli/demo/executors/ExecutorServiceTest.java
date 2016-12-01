package com.leekli.demo.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {


	public static void main(String[] args) {
		
		
		final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(3);
		ThreadPoolExecutor threadTool = new ThreadPoolExecutor(2, 2	, 2, TimeUnit.SECONDS, workQueue);
		
		for(int i=0;i<10;i++){
			final int n = i;
			System.out.println("add:"+i);
			System.out.println("====queue:" + workQueue.size());
			threadTool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("handle:"+n);
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		
//		for(int i=0;i<10;i++){
//			final int n = i;
//			threadTool.submit(new Callable<String>() {
//					@Override
//					public String call() throws Exception {
//						System.out.println(n);
//						Thread.sleep(10000);
//						return null;
//					}
//				});
//		}
		
//		ExecutorService excutor = Executors.newFixedThreadPool(2);
//		for(int i=0;i<10;i++){
//			final int n = i;
//			excutor.submit(new Callable<String>() {
//				@Override
//				public String call() throws Exception {
//					System.out.println(n);
//					Thread.sleep(10000);
//					return null;
//				}
//			});
//		}
		
	}
}

