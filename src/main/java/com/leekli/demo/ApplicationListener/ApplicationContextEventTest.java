package com.leekli.demo.ApplicationListener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

public class ApplicationContextEventTest implements ApplicationListener< ApplicationContextEvent>{

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		System.out.println("ApplicationEvent->ApplicationContextEvent init ...");
		
	}

}
