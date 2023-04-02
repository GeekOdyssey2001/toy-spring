package com.toy;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.*;

public class ClassPathXmlApplicationContext {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    //构造器获取外部配置，解析 bean 的定义
    public ClassPathXmlApplicationContext(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();
    }

    private void readXml(String fileName){
        SAXReader saxReader =new SAXReader();
        try {
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            //对配置文件的每一个 <bean>  进行处理
            for (Element element: (List<Element>)rootElement.elements()) {
                //获取 bean 的基本定义
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID,beanClassName);
                //将 bean 的定义存放到 beanDefinitions
                beanDefinitions.add(beanDefinition);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    //利用反射创建 Bean 实例，并储存在singleton
    private void instanceBeans(){
        for (BeanDefinition beanDefinition :beanDefinitions) {
            try {
                singletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //对外方法，让外部程序从容器中获取 bean 实例，会逐步演化成核心方法
    public Object getBean(String beanName){
            return singletons.get(beanName);
    }


}
