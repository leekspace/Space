package com.leekli.demo.eventbus;

import com.google.common.eventbus.EventBus;

public class EventBusTest {
	public static void testEventBus() {
		EventBus eventBus = new EventBus();
		Event et = new Event();
		eventBus.register(et);// 注册事件
		eventBus.post("ssdf");// 触发事件处理
		eventBus.unregister(et);
		eventBus.post("ssdf");// 触发事件处理
	}
	public static void main(String[] args) {
		EventBusTest.testEventBus();
	}
}
