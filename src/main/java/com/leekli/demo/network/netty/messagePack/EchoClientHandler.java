package com.leekli.demo.network.netty.messagePack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends  ChannelInboundHandlerAdapter {

	private final int sendNumber;
	
	
	public EchoClientHandler(int sendNumber) {
		this.sendNumber = sendNumber;
	}
	private UserInfo[] UserInfo(){
		UserInfo[] userInfos = new UserInfo[sendNumber];
		UserInfo userInfo = null;
		for(int i=0;i<sendNumber;i++){
			userInfo = new UserInfo();
			userInfo.setAget(i);
			userInfo.setName("abcdefg--->" + i);
			userInfos[i] = userInfo;
		}
		return userInfos;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		UserInfo[] infos = UserInfo();
		for(UserInfo infoE : infos){
			ctx.write(infoE);
		}
		ctx.flush();
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Client receive the msgpack message:" + msg);
		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		
	}

	

}

