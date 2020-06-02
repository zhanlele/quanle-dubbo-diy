package com.quanle.service.impl;

import com.quanle.filter.ServiceConstants;
import com.quanle.service.HelloService;

import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author quanle
 * @date 2020/6/2 9:32 PM
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        String ip = RpcContext.getContext().getAttachment(ServiceConstants.IP_KEY);
        System.out.println("服务请求IP:" + ip);
        return "hello serviceA:" + name;
    }
}
