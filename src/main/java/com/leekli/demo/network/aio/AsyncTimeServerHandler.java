package com.leekli.demo.network.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
/**
 * 接收连接
 * @author liwei
 * @Date   2016年12月15日 下午1:36:46 
 * @Desc
 */
public class AsyncTimeServerHandler implements Runnable{
	private int port;
	CountDownLatch latch;
	AsynchronousServerSocketChannel asynchronousServerSocketChannel;
	
	
	
	public AsyncTimeServerHandler(int port) {
		this.port = port;
		/**
		 * 首先创建异步服务端通道AsynchronousServerSocketChannel
		 * 然后调用它的绑定方法，绑定监听端口
		 * 
		 * 
		 */
		try {
			asynchronousServerSocketChannel =  AsynchronousServerSocketChannel.open();
			asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		/**
		 * 初始化latch 作用是阻塞当前线程，防止服务端执行完成退出
		 */
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private void doAccept() {
		/**
		 * 接收客户端连接，由于是异步操作，我们可以传递一个CompletionHandler<AsynchronousSocketChannel,? supper A> 类型的handle 实例接收accept成功
		 * 的通知消息。
		 */
		System.out.println("doaccept");
		asynchronousServerSocketChannel.accept(this	, new AcceptComletionHandler());
	}

}

