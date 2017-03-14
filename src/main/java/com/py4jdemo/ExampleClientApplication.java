package com.py4jdemo;

import py4j.GatewayServer;
/*
 * java call py
 */
public class ExampleClientApplication {
    public static void main(String[] args) throws InterruptedException {
        GatewayServer server = new GatewayServer();
        server.start();
        //Thread.sleep(10000);
        System.out.println("start call...");
        
        
        IHello hello = (IHello) server.getPythonServerEntryPoint(new Class[] { IHello.class });
//        IHello hello2 = (IHello) server.getPythonServerEntryPoint(new Class[] { IHello.class });
//        IHello hello3 = (IHello) server.getPythonServerEntryPoint(new Class[] { IHello.class });
        
        hello.setParameters("a");
        System.out.println(hello.sayHello());
        
        
        IHello  hello2 = (IHello) server.getPythonServerEntryPoint(new Class[] { IHello.class });

//      IHello hello2 = (IHello) server.getPythonServerEntryPoint(new Class[] { IHello.class });
//      IHello hello3 = (IHello) server.getPythonServerEntryPoint(new Class[] { IHello.class });
      
          hello2.setParameters("b");
          System.out.println(hello2.sayHello());
          System.out.println(hello.sayHello());
          hello.setParameters("a");
          System.out.println(hello2.sayHello());
          System.out.println(hello.sayHello());
//        System.out.println(hello2);
//        System.out.println(hello3);
//        try {
//            String a= hello.sayHello();
//            System.out.println(a);
//            //hello.sayHello(2, "Hello World");
////            byte[] b = hello.getImage();
////            FileOutputStream file = new FileOutputStream("d:\\test\\acb.flv");
////            file.write(b);
////            file.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        server.shutdown();
  }
}

