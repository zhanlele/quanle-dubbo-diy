package com.quanle.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.support.RpcUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quanle
 * @date 2020/6/2 10:03 PM
 */
@Activate(group = {CommonConstants.CONSUMER})
public class TPMonitorFilter implements Filter {

    private static Map<String, List<Monitor>> timeMap = new HashMap<>();

    private static Integer count = 0;


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (count == 0) {
            synchronized (TPMonitorFilter.class) {
                if (count == 0) {
                    count++;
                    new Thread(() -> {
                        count++;
                        printMonitor();
                    }).start();
                }
            }
        }
        long startTime = System.currentTimeMillis();
        try {
            // 执行方法
            return invoker.invoke(invocation);
        } finally {
            boolean async = RpcUtils.isAsync(invoker.getUrl(), invocation);
            //判断是否是异步调用
            if (!async) {
                long handleTime = System.currentTimeMillis() - startTime;
                String key = invoker.getInterface().getSimpleName() + "-" + invocation.getMethodName();
                List<Monitor> arrayList = timeMap.get(key);
                if (CollectionUtils.isEmpty(arrayList)) {
                    arrayList = new ArrayList<>();
                }
                Monitor monitor = new Monitor();
                monitor.setSpanTime(handleTime);
                monitor.setExpireTime(System.currentTimeMillis());
                arrayList.add(monitor);
                timeMap.put(key, arrayList);
            }
        }

    }

    /**
     * 每5秒钟，打印最近一分钟T90请求耗时
     */
    public static void printMonitor() {
        while (true) {
            //打印TP90耗时
            printTP90();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //打印TP99耗时
            printTP99();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * TP90请求耗时
     */
    public static void printTP90() {
        for (Map.Entry<String, List<Monitor>> entry : timeMap.entrySet()) {
            String key = entry.getKey();
            List<Monitor> list = entry.getValue();
            list.sort(Collections.reverseOrder());
            int per = (int) (Math.ceil(list.size() * 0.9));
            System.out.println("方法：" + key + " , TP90最近一分钟被调用情况，耗时如下：");
            for (int i = 0; i < per; i++) {
                Monitor monitor = list.get(i);
                if (System.currentTimeMillis() - monitor.getExpireTime() <= 60 * 1000) {
                    System.out.print(monitor.getSpanTime() + "ms,");
                }
            }
            System.out.println(" \n");
        }
    }

    /**
     * TP99请求耗时
     */
    public static void printTP99() {
        for (Map.Entry<String, List<Monitor>> entry : timeMap.entrySet()) {
            String key = entry.getKey();
            List<Monitor> list = entry.getValue();
            list.sort(Collections.reverseOrder());
            int per = (int) (Math.ceil(list.size() * 0.99));
            System.out.println("方法：" + key + " , TP99最近一分钟被调用情况，耗时如下：");
            for (int i = 0; i < per; i++) {
                Monitor monitor = list.get(i);
                if (System.currentTimeMillis() - monitor.getExpireTime() <= 60 * 1000) {
                    System.out.print(monitor.getSpanTime() + "ms,");
                }
            }
            System.out.println(" \n");
        }

    }

}
