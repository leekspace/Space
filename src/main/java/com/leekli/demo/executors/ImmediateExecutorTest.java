package com.leekli.demo.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import io.netty.util.concurrent.ImmediateExecutor;

public class ImmediateExecutorTest {
	public static void main(String[] args) {
		
		
		ImmediateExecutor ie = ImmediateExecutor.INSTANCE;
		ie.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println(1111);
				
			}
		});
	}
}

