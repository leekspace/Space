package com.leekli.demo.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {
	
	private Selector selector;
	private ServerSocketChannel servChannel;
	private volatile boolean stop;
	
	
	/**
	 * 初始化，多路复用器，绑定监听商品
	 * @param port
	 */
	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			servChannel = ServerSocketChannel.open();
			servChannel.configureBlocking(false);//异步非阻塞模式
			/**
			 * backlog设置为1024,挂起连接的最大数目，
			 * 也是说，在未调用server.accept();之前可以保存的客户端socket的数量 
			 */
			servChannel.bind(new InetSocketAddress(port),1024);
			//将socketchannel 注册到selector,监听OP_ACCEPT操作位
			servChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("thre time server is start in port :" + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}

	@Override
	public void run() {
		/**
		 * 循环遍历selector 它的休眠时间为1s,每隔1s被唤醒一次。
		 * selector也提供了一人无参的select方法，当有处于就绪状态的channel时，将返回channel的selectionKey集合
		 * 通过对就绪状态的channel 进行迭代，可以进行网络的异步读写操作。
		 */
		while(!stop){
				try {
					selector.select(1000);
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> it = selectedKeys.iterator();
					SelectionKey key = null;
					while(it.hasNext()){
						key = it.next();
						it.remove();
						try {
							handleInput(key);
						} catch (Exception e) {
							e.printStackTrace();
							if(key !=null){
								key.cancel();
								if(key.channel() !=null){
									key.channel().close();
								}
							}
						}
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
				
		}
		
		if(selector !=null){
			try {
				selector.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void handleInput(SelectionKey key) throws IOException{
		if(key.isValid()){
			/**
			 * 处理新接入的客户端请求，根据 对SelectionKey进行判断，即可知道网络事件的类型。
			 * 通过 ServerSocketChannel的accept按收客户端连接请求，并创建 SocketChannel实例。
			 * 完成以上操作，相当于完成了TCP三次握手。TCP链路正式建立。
			 * 注意：我们需要将SocketChannel设置为异步非阻塞，同时也可以对TCP参数进行设置，如TCP接收及发送缓冲区大小。
			 * 作为例子，没有对参数进行额外设置
			 */
			if(key.isAcceptable()){
				System.out.println(" client connection ...");
				//accept the new connection
				ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				//add a new connection to the selector
				sc.register(selector, SelectionKey.OP_READ);
			}
			
			
			/**
			 * 读取客户端请求信息。
			 * 创建一个ByteBuffer 由于事先我们并不知道客户端发送的数据大小，我们开辟了1024 byte的缓存区。
			 * 然后调用SocketChannel.read 读取数据。这个read是非阻塞的（因为我们设置了异步非租塞）
			 * 使用返回值进行判断：
			 * 	1.返回值大于0，读到了字节，对字节进行编解码
			 * 	2.返回值=0 没有读到字节，忽略
			 * 	3.返回值 -1，连接已经关闭。需要关闭SocketChannel，释放资源 
			 * 
			 * 当读取码流以后，进行解码，首先对readBuffer进行flip操作，它的作用是将缓冲区当前的limit设置为position ,position设置为0,用于后续对缓冲区读取操作
			 * 
			 */
			if(key.isReadable()){
				//read the data
				SocketChannel sc = (SocketChannel)key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				
				int readBytes =  sc.read(readBuffer);
				System.out.println(readBytes+"====");//如果用telnet测试，如果发送的是英语字符 ，则每次发过来1个字节
				if(readBytes >0){
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"UTF-8");
					System.out.println("the time server receive order:"+ body);
					String currentTime = "\r\n".equals(body)?(new Date(System.currentTimeMillis())).toString():"BAD ORDER";
					doWrite(sc,currentTime);
	
				}else if(readBytes <0){
					//对端链路关闭
					key.cancel();
					sc.close();
				}else{
					//读到0字节 忽略
				}
				
			}
		}
	}

	/**
	 * 将应答消息，异步发送给客户端
	 * @param channel
	 * @param response
	 * @throws IOException
	 */
	private void doWrite(SocketChannel channel, String response) throws IOException{
		if(response !=null && response.trim().length() >0){
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer =  ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
	}

	public void stop(){
		stop = true;
	}

}

