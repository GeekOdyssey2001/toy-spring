package com.toy.beans;

public class BServiceImpl implements BService{
    @Override
    public void sayHi() {
        System.out.println("hello,spring.This is beanB say hi");
    }
}
