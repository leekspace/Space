package com.leekli.demo.py4j;

public class StackEntryPoint {

    private Stack stack;

    public StackEntryPoint() {
      stack = new Stack();
      stack.push("Initial Item");
    }

    public Stack getStack() {
        return stack;
    }

}