package com.leekli.demo.network.netty.messagePack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	private final String host;
	private final int port;
	private final int sendNumber;
	public EchoClient(String host,int port,int sendNumber){
		this.host = host;
		this.port = port;
		this.sendNumber = sendNumber;
	}
	public void run() throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)
		.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
		.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
				ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
				ch.pipeline().addLast(new EchoClientHandler(sendNumber));
			}
		});
	}
}

