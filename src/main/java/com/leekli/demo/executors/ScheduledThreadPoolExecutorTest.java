package com.leekli.demo.executors;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 周期执行任务
 * @author liwei
 * @Date   2016年11月29日 上午10:50:34 
 * @Desc
 */
public class ScheduledThreadPoolExecutorTest {
	public static void main(String[] args) {
		 ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		 exec.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				System.out.println(new Date());
				
			}}, 1, 1, TimeUnit.SECONDS);
		 
	}
}

