package com.quanle.service.impl;

import com.quanle.service.HelloService;

import org.apache.dubbo.config.annotation.Service;

import java.util.Random;

/**
 * @author quanle
 * @date 2020/6/2 10:08 PM
 */
@Service
public class HelloServiceImpl implements HelloService {
    private Random random = new Random();
    @Override
    public String sayHello1(String name) {
        int i = random.nextInt(101);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello1:" + name;
    }

    @Override
    public String sayHello2(String name) {
        int i = random.nextInt(101);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello2:" + name;
    }

    @Override
    public String sayHello3(String name) {
        int i = random.nextInt(101);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello3:" + name;
    }
}
