package com.quanle.filter;

/**
 * @author quanle
 * @date 2020/6/2 10:03 PM
 */
public class Monitor implements Comparable<Monitor> {

    private long spanTime;
    private long expireTime;

    public long getSpanTime() {
        return spanTime;
    }

    public void setSpanTime(long spanTime) {
        this.spanTime = spanTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public int compareTo(Monitor monitor) {
        return monitor.getSpanTime() > spanTime ? -1 : 0;
    }

}
