package com.leekli.demo.network.aio;

public class TimeServer {
	public static void main(String[] args) {
		int port = 8080;
		
		AsyncTimeServerHandler timeserver = new AsyncTimeServerHandler(port);
		new Thread(timeserver,"AIO-AsyncTimeserverHandler-001").start();
	}
}

