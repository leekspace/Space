package com.leekli.demo.network.buffer;

import java.nio.ByteBuffer;

import io.netty.util.ReferenceCountUtil;

public class ByteBufferDemo {

	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(100);
		show("empty buf",buf);
		buf.put((byte) 'H');
		buf.put((byte) 'e');
		buf.put((byte) 'l');
		buf.put((byte) 'l');
		buf.put((byte) ',');
		buf.put((byte) 'w');
		buf.put((byte) 'o');
		buf.put((byte) 'r');
		buf.put((byte) 'l');
		buf.put((byte) 'd');
		buf.put((byte) '!');
		show("put hello after",buf);
		
		buf.flip();//准备读取，这个时候 position回到0，不可以使用put了，因为会覆盖上次put到0的元素
		show("flip after",buf);
		
		System.out.print((char)buf.get());
		System.out.print((char)buf.get());
		System.out.print((char)buf.get());
		System.out.print((char)buf.get());
		System.out.println((char)buf.get());
		show("get after",buf);
		
		buf.rewind();//将position为0，重新读取这段数据
		show("rewind after",buf);
		System.out.print((char)buf.get());
		System.out.print((char)buf.get());
		System.out.print((char)buf.get());
		System.out.print((char)buf.get());
		System.out.println((char)buf.get());
		show("get after",buf);
		
		buf.rewind();//将position为0，准备重新读取这段数据
		show("get[dst] before",buf);
		System.out.println(buf.hasRemaining());
		int t = 1;
		while(buf.hasRemaining()){
			System.out.println("第"+t+++"次读buf到byte dst");
			
			int size = 5;
			if(buf.remaining() <size){//每次读取5个byte,最后一次不够5个时，size=buf.remaining()剩余的数量
				size = buf.remaining();
			}
			System.out.println("本次读" +size +"个byte");
			
			byte[] dst = new byte[size];
			buf.get(dst );				//读到buf到dst
			for(int i=0;i<dst.length;i++){
				System.out.print((char)dst[i]);
			}
			System.out.println();
			show("get[dst] after",buf);
		}
		System.out.println("buf.hasRemaining()="+buf.hasRemaining()+"表示已将缓冲区所有数据读完");
		show("读取完成后的buf",buf);
		
		

		
	}
	
	private static void show(String str,ByteBuffer buf){
		System.out.println(buf+" "+str);
	}
}

