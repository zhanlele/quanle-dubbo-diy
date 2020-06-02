package com.quanle.bean;

import com.quanle.service.HelloService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * @author quanle
 * @date 2020/6/2 10:06 PM
 */
@Component
public class ConsumerComponent {

    @Reference(timeout = 5000)
    private HelloService helloService;

    public String sayHello1(String name) {
        return helloService.sayHello1(name);
    }

    public String sayHello2(String name) {
        return helloService.sayHello2(name);
    }

    public String sayHello3(String name) {
        return helloService.sayHello3(name);
    }
}
