package com.leekli.demo.py4j;

import py4j.GatewayServer;

public class Py4jDemo {
    public int addition(int first, int second) {
        return first + second;
      }

      public static void main(String[] args) {
        // app is now the gateway.entry_point
        GatewayServer server = new GatewayServer(new Py4jDemo());
        server.start();
        System.out.println("Gateway Server Started 1");
        
        Endpoint endpoint = new EndpointImpl(); 
        GatewayServer gatewayServer = new GatewayServer(endpoint,8282);
        gatewayServer.start();
        System.out.println("Gateway Server Started 2");
      }
}

