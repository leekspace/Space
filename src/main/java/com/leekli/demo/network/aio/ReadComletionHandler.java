package com.leekli.demo.network.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
/**
 * 处理消息，并通过传入的AsynchronousSocketChannel 返回结果
 * @author liwei
 * @Date   2016年12月15日 下午1:38:45 
 * @Desc
 */
public class ReadComletionHandler implements CompletionHandler<Integer,  ByteBuffer> {

	private AsynchronousSocketChannel channel;
	
	
	/*
	 * 我们将AsynchronousSocketChannel通过参数传递到ReadComletionHandler中当作成员变量来使用。
	 * 主要用于读取半包消息和发送应答。
	 */
	public ReadComletionHandler(AsynchronousSocketChannel channel){
		if(this.channel == null){
			this.channel = channel;
		}
	}
	
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		System.out.println("===========read==========");
		attachment.flip();
		byte[] body = new byte[attachment.remaining()];//根据缓冲区的可读字节数创建 byte数组。
		attachment.get();
		try {
			String req = new String(body,"UTF-8");
			System.out.println("the time server receive order:" + req);
			String currentTime = "QUERY TIME ORDER".equals(req)?new Date().toString():"BAD ORDER";
			doWrite(currentTime);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}

	private void doWrite(String currentTime) {
		if(currentTime !=null && currentTime.trim().length() >0){
			/**
			 * 将时间字符串转为字节数组。存入发送缓冲区ByteBuffer
			 */
			byte[] 	bytes = (currentTime).getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			/**
			 * 调用AsynchronousSocketChannel 的异步回调方法，实现异步回调接口CompletionHandler
			 * 
			 */
			channel.write(writeBuffer,writeBuffer,new CompletionHandler<Integer, ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer buffer) {
					// 如果发现没有完成，则继续发送
					if(buffer.hasRemaining()){
						channel.write(buffer,buffer,this);
					}
				}

				@Override
				public void failed(Throwable exc, ByteBuffer buffer) {
					/**
					 * 对异常进行判断
					 * 如果是IO异常，关闭连接，释放资源。
					 * 如果是其它异常，按业务自己的逻辑进行处理。
					 */
					System.out.println("failed");
					try {
						channel.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		try {
			this.channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

