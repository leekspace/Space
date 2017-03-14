package com.leekli.demo.eventbus;

import com.google.common.eventbus.EventBus;

public class EventBusTest {
	public static void testEventBus() {
		EventBus eventBus = new EventBus();
		Event et = new Event();
		EventContext ec = new EventContext("TOKEN:XXXX");
		eventBus.register(et);// 注册事件
		eventBus.post(ec);// 触发EventContext事件
		eventBus.post("字符串事件");//触发字符串事件
		
		eventBus.unregister(et);//取消注册
		eventBus.post(ec);// 不再触发事件处理
	}
	public static void main(String[] args) {
		EventBusTest.testEventBus();
	}
}
