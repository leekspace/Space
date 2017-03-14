package com.leekli.demo.network.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
	private int i=0;

	public ChildChannelHandler() {
		super();
	}
	public ChildChannelHandler(int i) {
		this.i = i;
		System.out.println("===="+this.i);
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		System.out.println("ChildChannelHandler..." + i);
		ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
		ch.pipeline().addLast(new StringDecoder());
		ch.pipeline().addLast(new TimeServerHandler());
	}

 

}

