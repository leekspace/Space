package com.leekli.demo.kafka;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.alibaba.fastjson.JSONObject;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.consumer.SimpleConsumer;
 
/**
 * 从zookeeper 取分区列表，取分区leader
 * @author liwei
 * @Date   2016年11月29日 下午8:01:34 
 * @Desc
 */
public class SimpleExample {
    public static void main(String args[]) {
    	
    	String connectString = "10.100.100.241:2181";
    	String loaderNode = "10.100.100.241";//由zookeeper 中取值 
    	String groupid = "event_motion";
    	String topic = "motion";
    	String clientName = "motion_client";
    	
    	//从zookeeper取分区数
    	List<String> parts = getPartitions(connectString, groupid, topic);
    	//从zookeeper取offset
    	Map<Integer,Integer> offsetMap = getOffset(connectString, groupid, topic);
    	
    	SimpleConsumer consumer = new SimpleConsumer(loaderNode, 9092, 1000, 100, "33");
    	Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
    	
    	System.out.println(offsetMap);
    	int logsizeCount = 0,offsetCount = 0;
    	
        for(String p : parts){
        	Integer pi = Integer.valueOf(p);
        	TopicAndPartition topicAndPartition = new TopicAndPartition(topic, pi);
			requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime(), 1));
			
			OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
			
			OffsetResponse a = consumer.getOffsetsBefore(request);
			System.out.println("Partition:"+pi+" logsize:"+a.offsets(topic, pi)[0] + " offset:"+offsetMap.get(pi));
			logsizeCount += a.offsets(topic, pi)[0];
			offsetCount += offsetMap.get(pi);
        }
        System.out.println("logsizeCount:" + logsizeCount);
        System.out.println("offsetCount:" + offsetCount);
        System.out.println("lag:" + (logsizeCount-offsetCount));
    }
    
    
    public static List<String> getPartitions(String connectString,String groupid,String topic){
    	int sessionTimeout = 1000;
		ZooKeeper zk = null;
		Watcher watcher = new Watcher(){
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getPath());
		}};
			
		try {
			zk = new ZooKeeper(connectString, sessionTimeout, watcher);
			List<String> list = zk.getChildren("/", false);
			for(String str : list){
				System.out.println(str);
			}
			String partitions = "/brokers/topics/"+topic+"/partitions";
			return  zk.getChildren(partitions, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(zk !=null)
				try {
					zk.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		return null;
    }
 
    
    public static Map<Integer,Integer> getOffset(String connectString,String groupid,String topic){
    	Map<Integer,Integer> result = new HashMap<Integer,Integer>();
    	int sessionTimeout = 1000;
		ZooKeeper zk = null;
		Watcher watcher = new Watcher(){
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getPath());
		}};
    	try {
			zk = new ZooKeeper(connectString, sessionTimeout, watcher);
			String partitions = "/brokers/topics/"+topic+"/partitions";
			String offsetUrl  =  "/consumers/"+groupid+"/offsets/"+topic;
			List<String> list = zk.getChildren(partitions, false);
			for(String str : list){
				System.out.println(offsetUrl+"/"+str);
				String offset = new String(zk.getData(offsetUrl+"/"+str, watcher, null));
				result.put(Integer.valueOf(str), Integer.valueOf(offset));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(zk !=null)
				try {
					zk.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
    	
    	
    	return result;
		
		
    }
 
 
}
