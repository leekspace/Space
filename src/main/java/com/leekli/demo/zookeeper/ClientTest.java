package com.leekli.demo.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.alibaba.fastjson.JSONObject;


public class ClientTest {
	public static void main(String[] args) throws KeeperException, InterruptedException {
		Watcher watcher = new Watcher(){
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getPath());
				
			}};
		String connectString = "10.100.100.241:2181";
		int sessionTimeout = 1000;
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(connectString, sessionTimeout, watcher);
			System.out.println(zk.getState());
			
			List<String> list = zk.getChildren("/", false);
			for(String str : list){
				System.out.println(str);
			}
			String state = "/brokers/topics/motion/partitions/6/state";
			String offsets = "/consumers/event_motion/offsets/motion/9";
			
			String re = new String(zk.getData(offsets, false, null));
			System.out.println(re);
			
			String re2 = new String(zk.getData(state, false, null));
			System.out.println(re2);
			JSONObject obj = (JSONObject)JSONObject.parse(re2);
			System.out.println(obj.getString("leader"));

		} catch (IOException e) {

			e.printStackTrace();
		}finally {
			if(zk !=null)
				zk.close();
		}
		

		
 
	}
}

