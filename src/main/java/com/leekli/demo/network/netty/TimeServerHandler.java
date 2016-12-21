package com.leekli.demo.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter  {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
 
	        while (in.isReadable()) { // (1)
	            System.out.print((char) in.readByte());
	            System.out.flush();
	        }
	     ByteBuf  resp = Unpooled.copiedBuffer("aaa".getBytes());
	    
	    ctx.write(resp); // (1)
        ctx.flush(); // (2)
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
        ctx.close();
	}
 

}

