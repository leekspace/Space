package com.leekli.demo.eventbus;

import com.google.common.eventbus.Subscribe;

public class Event {
	
	@Subscribe
	public void sub(String message){
		System.out.println(message);
	}

}
