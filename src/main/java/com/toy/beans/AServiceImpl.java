package com.toy.beans;

public class AServiceImpl implements AService{

    @Override
    public void sayHello() {
        System.out.println("hello, spring bean!");
    }
}
