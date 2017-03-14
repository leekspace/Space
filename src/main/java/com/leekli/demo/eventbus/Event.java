package com.leekli.demo.eventbus;

import com.google.common.eventbus.Subscribe;

public class Event {
	
	@Subscribe
	public void sub(String message){
		System.out.println("sub1 "+message);
	}
	
	@Subscribe
    public void sub2(EventContext message){
        System.out.println("sub2 token:"+message.getToken());
    }

}
