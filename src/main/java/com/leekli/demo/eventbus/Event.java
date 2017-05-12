package com.leekli.demo.eventbus;

import com.google.common.eventbus.Subscribe;

public class Event {
	
	/**
	 * 字符串事件
	 * @param message
	 */
	@Subscribe
	public void sub(String message){
		System.out.println(Thread.currentThread()+"sub1 "+message);
	}
	
	/**
	 * EventContext 事件
	 * @param message
	 */
	@Subscribe
    public void sub2(EventContext message){
		try {
			Thread.sleep(3000);
			System.out.println(message.getContext().getName());
		} catch (InterruptedException e) {
		}
        System.out.println(Thread.currentThread()+"sub2 token:");
    }
	
	@Subscribe
	public void event(Integer i){
		System.out.println(Thread.currentThread()+"event :"+i);
	}

}
