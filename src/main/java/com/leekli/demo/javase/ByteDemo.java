package com.leekli.demo.javase;

import java.io.UnsupportedEncodingException;

public class ByteDemo {
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String s = "string";
		String s2 = "我是";
		byte[] b = s.getBytes();
		System.out.println(b.length);
		System.out.println(s2.getBytes("GBK").length);
		
		
	}
}

