package com.leekli.demo.network.falseAsync;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
	public static void main(String[] args) {
		int port = 8080;
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("the timeserver is start in port:" + port);
			
			Socket socket  = null;
			/**
			 * 创建I/O处理线程池
			 * 当接收客户端连接时，将socket封装成一个Task ，然后调用线程池的execute方法执行。
			 * 从而避免了每个请求都创建一个新线程
			 */
			TimeServerHandleExecutePool singleExecuteor =  new TimeServerHandleExecutePool(50,1000);
			while(true){
				socket = server.accept();
				singleExecuteor.execute(new TimeServerHandler(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(server !=null){
				System.out.println("the timeserver close.");
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

