package com.leekli.demo.network.netty.server.fixed;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Receive client:[" + msg + "]");
		while(true){
			Thread.sleep(190);
			ByteBuf echo  = Unpooled.copiedBuffer(((String)msg + "\r\n").getBytes());
			ctx.writeAndFlush(echo);
		}
		
		
	}

 
 

}

