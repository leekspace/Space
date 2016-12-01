package com.leekli.demo.common;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author leekli
 *
 * 配置项
 * 1.连接池size
 * 2.每个路由size(连接到每个远程主机的连接数)
 * 3.从连接池获取连接超时 ；数据传输超时；远程主机建立连接超时
 * 4.keepalived空闲时间ms
 *
 */
public class HttpClientPool {
	/*
	public static void main(String[] args) {
		final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(3);//连接池SIZE
		cm.setDefaultMaxPerRoute(1); //连接每个远程主机数据的SIZE 
		final ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
			
			//空闲多长时间ms 断开连接
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				return 8000;
			}

		};
		final RequestConfig requestConfig = RequestConfig.custom()
				 .setConnectionRequestTimeout(1000)//从连接池获取连接超时
				 .setSocketTimeout(1000)//数据传输超时
				 .setConnectTimeout(1000)//建立连接超时
				 .build();

		// Build the client.
		

		new Thread(new Runnable() {
			@Override
			public void run() {
				final CloseableHttpClient threadSafeClient = HttpClients.custom()
							.setConnectionManager(cm)
							.useSystemProperties()
							.disableConnectionState()
							.setDefaultRequestConfig(requestConfig)
							.setKeepAliveStrategy(myStrategy)
							.build();
				
				runMethod(threadSafeClient, "http://localhost:8080/index/1");
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				final CloseableHttpClient threadSafeClient = HttpClients.custom().setConnectionManager(cm).useSystemProperties()
						.disableConnectionState().setDefaultRequestConfig(requestConfig).setKeepAliveStrategy(myStrategy)
						.build();
				runMethod(threadSafeClient, "http://localhost:8080/index/2");
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				final CloseableHttpClient threadSafeClient = HttpClients.custom().setConnectionManager(cm).useSystemProperties()
						.disableConnectionState().setDefaultRequestConfig(requestConfig).setKeepAliveStrategy(myStrategy)
						.build();
				runMethod(threadSafeClient, "http://localhost:8080/index/2");
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				final CloseableHttpClient threadSafeClient = HttpClients.custom().setConnectionManager(cm).useSystemProperties()
						.disableConnectionState().setDefaultRequestConfig(requestConfig).setKeepAliveStrategy(myStrategy)
						.build();
				runMethod(threadSafeClient, "http://localhost:8080/index/2");
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				final CloseableHttpClient threadSafeClient = HttpClients.custom().setConnectionManager(cm).useSystemProperties()
						.disableConnectionState().setDefaultRequestConfig(requestConfig).setKeepAliveStrategy(myStrategy)
						.build();
				runMethod(threadSafeClient, "http://localhost:8080/index/2");
			}
		}).start();
		//
		// runMethod(threadSafeClient);

	}

	public static void runMethod(final CloseableHttpClient client, final String urlprefix) {

		List<HttpGet> httpGets = new ArrayList<HttpGet>(100);
		for (int i = 0; i < 100; i++) {
			HttpGet httpGet = new HttpGet(urlprefix);
			httpGets.add(httpGet);
		}

		int i = 0;
		while (i < httpGets.size()) {
			try {
				//int sleep = RandomUtils.nextInt(2000);
				//System.out.println("sleep :" + sleep);
				//Thread.sleep(sleep);
				System.out.println("================"+Thread.currentThread().getName());
				// 执行get请求
				HttpResponse httpResponse = client.execute(httpGets.get(i));
				// 获取响应消息实体
				HttpEntity entity = httpResponse.getEntity();
				// 响应状态
				System.out.println("status:" + httpResponse.getStatusLine());
				// 判断响应实体是否为空
				if (entity != null) {
					System.out.println("contentEncoding:" + entity.getContentEncoding());
					System.out.println("response content:" + EntityUtils.toString(entity));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	*/
}
