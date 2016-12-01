package com.leekli.demo.ApplicationListener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationReadyEventTest implements ApplicationListener<ApplicationReadyEvent>{

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("SpringApplicationEvent->ApplicationReadyEvent init...");
		
	}

}
