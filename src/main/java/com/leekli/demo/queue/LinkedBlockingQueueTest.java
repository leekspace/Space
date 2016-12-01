package com.leekli.demo.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueTest {
	public static void main(String[] args) {
		int queueSize = 10;
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(queueSize);
		for(int i=0;i<20;i++){
			System.out.println(queue.size());
			queue.add("dd");
		}
		
	}
}
