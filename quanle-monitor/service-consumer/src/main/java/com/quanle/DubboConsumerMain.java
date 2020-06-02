package com.quanle;

import com.quanle.bean.ConsumerComponent;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author quanle
 * @date 2020/6/2 10:06 PM
 */
public class DubboConsumerMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        ConsumerComponent service = context.getBean(ConsumerComponent.class);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //调用接口一
        executorService.submit((Runnable) () -> {
            while (true) {
                String s = service.sayHello1("hello1 ...");
                //System.out.println("result :" + s);
            }
        });

        //调用接口二
        executorService.submit((Runnable) () -> {
            while (true) {
                String s = service.sayHello2("hello2 ...");
                //System.out.println("result :" + s);
            }
        });

        //调用接口三
        executorService.submit((Runnable) () -> {
            while (true) {
                String s = service.sayHello3("hello3 ...");
                //System.out.println("result :" + s);
            }
        });

    }

    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    //@EnableDubbo(scanBasePackages = "com.quanle.bean")
    @ComponentScan("com.quanle.bean")
    @EnableDubbo
    static class ConsumerConfiguration {

    }
}
