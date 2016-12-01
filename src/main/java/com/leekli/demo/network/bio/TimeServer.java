package com.leekli.demo.network.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
	public static void main(String[] args) {
		int port = 8080;
		if(args !=null && args.length>0){
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				//采用默认值 
			}
		}
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("the timeserver port is :"+port);
			Socket socket = null;
			while(true){
				System.out.println(Thread.currentThread().getName()+"=====accept client=====");
				/**
				 * 如果没有客户端接入，则阻塞在这里
				 * 如果有客户端接入，则创建新线程执行，然后主线程继续下次循环,并阻塞在这里等待新客户端
				 */
				socket =  server.accept();
				new Thread(new TimeServerHandle(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(server !=null){
				System.out.println("the timeserver close");
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				server = null;
			}
		}
	}
}

