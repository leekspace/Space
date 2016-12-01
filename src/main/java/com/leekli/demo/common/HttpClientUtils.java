package com.leekli.demo.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	private static final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	private static final long keepaliveMs = 5000;
	private static final RequestConfig requestConfig = RequestConfig.custom()
			 .setConnectionRequestTimeout(3000)//从连接池获取连接超时
			 .setSocketTimeout(3000)//数据传输超时
			 .setConnectTimeout(3000)//建立连接超时
			 .build();
	
	static{
		cm.setMaxTotal(30);//连接池SIZE
		cm.setDefaultMaxPerRoute(10); //连接每个远程主机数据的SIZE 
	}
	
	public static String get(String url){
		
		CloseableHttpClient client = HttpClients.custom()
				.setConnectionManager(cm)
				.useSystemProperties()
				.disableConnectionState()
				.setDefaultRequestConfig(requestConfig)
				.setKeepAliveStrategy(new ConnectionKeepAliveStrategy(){
					@Override
					public long getKeepAliveDuration(HttpResponse arg0, HttpContext arg1) {
						return keepaliveMs;
					}
				})
				.build();
		HttpGet httpGet = new HttpGet(url);
		try {
			// 执行get请求
			HttpResponse httpResponse = client.execute(httpGet);
			// 获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			// 响应状态
			System.out.println("status:" + httpResponse.getStatusLine());
			// 判断响应实体是否为空
			if (entity != null) {
				String body = EntityUtils.toString(entity);
				System.out.println("contentEncoding:" + entity.getContentEncoding());
				System.out.println("response content:" + body);
				return body;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "";
	}
	
	public static String post(String url,String postdata){
		CloseableHttpClient client = HttpClients.custom()
				.setConnectionManager(cm)
				.useSystemProperties()
				.disableConnectionState()
				.setDefaultRequestConfig(requestConfig)
				.setKeepAliveStrategy(new ConnectionKeepAliveStrategy(){
					@Override
					public long getKeepAliveDuration(HttpResponse arg0, HttpContext arg1) {
						return keepaliveMs;
					}
				})
				.build();
		
		HttpPost httpPost = new HttpPost(url);
		StringEntity postEntity = new StringEntity(postdata, ContentType.APPLICATION_JSON);
		httpPost.setEntity(postEntity);
		try {
			// 执行get请求
			HttpResponse httpResponse = client.execute(httpPost);
			// 获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			// 响应状态
			System.out.println("status:" + httpResponse.getStatusLine());
			// 判断响应实体是否为空
			if (entity != null) {
				String body = EntityUtils.toString(entity);
				System.out.println("contentEncoding:" + entity.getContentEncoding());
				System.out.println("response content:" + body);
				return body;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
