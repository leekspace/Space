package com.leekli.demo.utils;

import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;

public class ByteTest {

	public static void main(String[] args) {
		try {
			System.out.println("a".getBytes("UTF-8").length);
			System.out.println("我".getBytes("UTF-8").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String str = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println(str);
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


