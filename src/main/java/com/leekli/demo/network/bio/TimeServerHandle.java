package com.leekli.demo.network.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandle implements Runnable {

	Socket socket;
	public TimeServerHandle(Socket socket) {
		this.socket = socket;
		System.out.println(Thread.currentThread().getName()+" TimeServerHandle constructor...");
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" TimeServerHandle run...");
		handle();
	}

	private void handle(){
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(),true);
			
			String currentTime = null;
			String body = null;
			while(true){
				System.out.println("while true");
				/**
				 * 如果in中无数据 ，则阻塞在这里，等待数据
				 * 当客户端输入数据，并有换行符时，读取换行符之前的字符(即读一行)
				 * 如果已经读到输入流的尾部，则break退出(当客户端断开连接时，退读到了输入流的尾部)
				 * 
				 */
				body = in.readLine();
				if(body ==null){
					System.out.println("读到了输入流的属部，退出处理");
					break;
				}
				System.out.println("the time server receive order :" + body);
				currentTime = "QUERY TIME ORDER".equals(body)?(new Date(System.currentTimeMillis())).toString():"BAD ORDER";
				out.println(currentTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(in !=null){
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				in = null;
			}
			if(out!=null){
				out.close();
			}
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				socket = null;
			}
		}
		System.out.println("exit run");
	}
}

