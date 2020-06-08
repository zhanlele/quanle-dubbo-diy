package com.quanle;

import com.quanle.filter.ServiceConstants;
import com.quanle.service.HelloService;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author quanle
 * @date 2020/6/2 9:41 PM
 */
@RestController
@RequestMapping("/")
public class DemoController {
    @Reference(check = false,loadbalance = "roundrobin")
    private HelloService helloService;

    @RequestMapping()
    public String sayHello(HttpServletRequest request) {
        String ipAddress = getIpAddress(request);
        RpcContext.getContext().setAttachment(ServiceConstants.IP_KEY, ipAddress);
        String test = helloService.sayHello("test");
        System.out.println(test);
        return test;
    }


    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip))
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ignored) {
            }
        return ip;
    }
}
