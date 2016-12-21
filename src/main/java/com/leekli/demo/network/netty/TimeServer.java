package com.leekli.demo.network.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
	public static void main(String[] args) throws Exception {
		int port = 8080;
		new TimeServer().bind(port);
	}

	private void bind(int port) throws Exception{
		EventLoopGroup boosGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		ServerBootstrap b = new ServerBootstrap();
		b.group(boosGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG	,1024)
			.childHandler(new ChildChannelHandler());
		
		//绑定端口，同步等待成功
		ChannelFuture f= b.bind(port).sync();
		
		//等待服务端监听端口关闭
		f.channel().closeFuture().sync();
	}
}

