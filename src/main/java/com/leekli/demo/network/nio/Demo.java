package com.leekli.demo.network.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Demo {
	public static void main(String[] args) {
		
		try {
			ServerSocketChannel acceptorSvr = ServerSocketChannel.open();
			int port = 8080;
			acceptorSvr.socket().bind(new InetSocketAddress(port ));
			acceptorSvr.configureBlocking(false);
			
			Selector selector  = Selector.open();
			new	 Thread(new ReactorTask()).start();
			
			
			
			SelectionKey key =  acceptorSvr.register(selector, SelectionKey.OP_ACCEPT);
			
			
			int num = selector.select();
			Set set  = selector.selectedKeys();
			Iterator it = set.iterator();
			while(it.hasNext()){
				SelectionKey sk = (SelectionKey)it.next();
				//TODO
				System.out.println(sk);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

