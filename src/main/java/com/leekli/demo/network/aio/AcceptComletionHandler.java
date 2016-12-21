package com.leekli.demo.network.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
/**
 * 处理连接管理
 * @author liwei
 * @Date   2016年12月15日 下午1:37:02 
 * @Desc
 */
public class AcceptComletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler> {

 

	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
		System.out.println("completed");
		attachment.asynchronousServerSocketChannel.accept(attachment, this);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		/**
		 * 读取到buffer，调用CompletionHandler 接口的completed方法
		 */
		result.read(buffer,buffer,new ReadComletionHandler(result));
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
		exc.printStackTrace();
		attachment.latch.countDown();
		
	}

}

 