package com.leekli.demo.py4j;

public class EndpointImpl implements Endpoint{

    @Override
    public String sayHello() {
        // TODO Auto-generated method stub
        return "hello";
    }

    @Override
    public String sayWorld() {
        // TODO Auto-generated method stub
        return "world";
    }

    @Override
    public Stack getStack() {
        // TODO Auto-generated method stub
        return new Stack();
    }

}

