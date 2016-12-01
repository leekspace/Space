package com.leekli.demo.utils;

import java.io.UnsupportedEncodingException;

public class ByteTest {

	public static void main(String[] args) {
		try {
			System.out.println("a".getBytes("UTF-8").length);
			System.out.println("我".getBytes("UTF-8").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}
}


