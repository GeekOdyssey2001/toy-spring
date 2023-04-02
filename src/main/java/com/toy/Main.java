package com.toy;

import com.toy.beans.AService;
import com.toy.beans.BService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) ctx.getBean("toyBean");
        aService.sayHello();
        BService bService = (BService) ctx.getBean("beanB");
        bService.sayHi();
        //todo:循环依赖 check
    }
}