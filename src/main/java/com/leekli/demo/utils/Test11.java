package com.leekli.demo.utils;

import java.util.concurrent.locks.ReentrantLock;

public class Test11 {
	public static  int num; 
	private ReentrantLock lock = new ReentrantLock();
	public synchronized void count() { 
 
        System.out.println(Thread.currentThread().getName()+" bef:"+num);
        num++;
        System.out.println(Thread.currentThread().getName()+" atf:"+num);
        count2();
 
    }  
	public synchronized void count2() {  
 
        System.out.println(Thread.currentThread().getName()+" bef:"+num);
        num++;
        System.out.println(Thread.currentThread().getName()+" atf:"+num);
        System.out.println("getHoldCount:"+lock.getHoldCount());
 
    }  
	
	
	public static void main(String[] args) {
	     Runnable runnable = new Runnable() {  
	    	 Test11 count = new Test11();  
	            public void run() {  
	                count.count();  
	            }  
	        };  
	        for(int i = 0; i < 10; i++) {  
	            new Thread(runnable).start();  
	        }  
	    }  
}

