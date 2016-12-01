package com.leekli.demo.network.falseAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandleExecutePool {
	
	private ExecutorService executor;

	public TimeServerHandleExecutePool(int maximumPoolSize, int taskQueueSize) {
		executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maximumPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(taskQueueSize));
	}
	public void execute(Runnable task) {
		executor.execute(task);
	}

}
