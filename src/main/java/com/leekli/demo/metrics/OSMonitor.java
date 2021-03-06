package com.leekli.demo.metrics;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.LoggerFactory;

/**
 * 检测系统运行状态
 * 
 * @author 陈修恒
 * @date 2016年5月3日
 */
public class OSMonitor {
	private static OSMonitor instance;
    

    private final MemoryMXBean osmb;
    private final OperatingSystemMXBean os;
    
    
    
    private OSMonitor() {
    	osmb = ManagementFactory.getMemoryMXBean();
    	os = ManagementFactory.getOperatingSystemMXBean();
    }

    public static synchronized OSMonitor getInstance() {
    	if (null == instance) {
    		instance = new OSMonitor();
    	}
    	
    	return instance;
    }


    /**
     * the "recent cpu usage" for the whole system; a negative value if not available.
     * 
     * @return
     */
    @SuppressWarnings("restriction")
	public int getSystemCpuLoad() {
    	if (os instanceof  com.sun.management.OperatingSystemMXBean) {
    		com.sun.management.OperatingSystemMXBean unix = (com.sun.management.OperatingSystemMXBean)os;
    		return (int)(100*  unix.getSystemCpuLoad());
    	} else {
    		return -1;
    	}
    }
    
    public int getSystemCpuIdle() {
    	int load = getSystemCpuLoad();
    	
    	return load < 0 ? 100 : 100 -load;
    }
    
    /**
     * the "recent cpu usage" for the Java Virtual Machine process; a negative value if not available.
     * 
     * @return
     */
    @SuppressWarnings("restriction")
	public int getProcessCpuLoad() {
    	if (os instanceof  com.sun.management.OperatingSystemMXBean) {
    		com.sun.management.OperatingSystemMXBean unix = (com.sun.management.OperatingSystemMXBean)os;
    		return (int)(100 * unix.getProcessCpuLoad());
    	} else {
    		return -1;
    	}
    }
    

    public Long getNonHeapMemoryUsed() {
        return osmb.getNonHeapMemoryUsage().getUsed();
    }
    
    public Long getHeapMemoryUsed() {
        return osmb.getHeapMemoryUsage().getUsed();
    }
    
    public List<String> getLocalIPList(){
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            LoggerFactory.getLogger(getClass()).warn("获取本机 IP 列表出错!", e);
        }
        
        return ipList;
    }

}
