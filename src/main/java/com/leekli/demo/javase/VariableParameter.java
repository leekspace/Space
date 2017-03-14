package com.leekli.demo.javase;

public class VariableParameter {

    public static void main(String[] args) {
        add(3,4,5,6,7);
    }
    
    public static void add(int a,int ...n){
        System.out.println(a);
        System.out.println(n.length);
    }
}

