package com.leekli.demo.ApplicationListener;

import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;

public class SpringApplicationEventTest implements ApplicationListener<SpringApplicationEvent>{
	static int times = 1;
	
	@Override
	public void onApplicationEvent(SpringApplicationEvent event) {
		System.out.println("SpringApplicationEvent init ..."+ times++);
		
	}

}
