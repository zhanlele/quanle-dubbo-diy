package com.quanle.filter;

/**
 * @author quanle
 * @date 2020/6/2 9:26 PM
 */
public class IpUtil {

    private static final ThreadLocal<String> ipCache = new ThreadLocal<>();

    public static String getIp() {
        return ipCache.get();
    }

    public static void setIp(String ip) {
        ipCache.set(ip);
    }

    public static void clear() {
        ipCache.remove();
    }
}
