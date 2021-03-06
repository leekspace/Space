package com.leekli.demo.network.netty.client;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

	private  ByteBuf firstMessage;
	private byte[] req = "QUERY TIME ORDER".getBytes();
	
	public TimeClientHandler(){
		byte[] req = "QUERY TIME ORDER".getBytes();
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		/**
		 * 向服务端发送100次 时间查询命令
		 * 
		 */
		for(int i=0;i<100;i++){
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			/**
			 * 确保每条消息会被写入Channel中，按照我们的设计，服务端应该收到100条查询时间的指令。
			 * 事实结果，却收到了一条，这说明发生了粘包
			 */
			ctx.writeAndFlush(message);
			
		}
		ctx.writeAndFlush(firstMessage);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println(body);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("Unexpected exception from downstream .");
		ctx.close();
	}

 

}

