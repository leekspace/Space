package com.leekli.demo.ApplicationListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationEventTest implements ApplicationListener<ApplicationEvent>{

	static int times = 1;
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("ApplicationEvent init ..." + times++);
		
	}

}
