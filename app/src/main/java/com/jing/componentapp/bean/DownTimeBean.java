package com.jing.componentapp.bean;

/**
 * Created by liujing on 2017/9/30.
 */

public class DownTimeBean {
    private String endTime;
    //结束时间和当前的时间差
    private long countTime;

    public DownTimeBean() {
    }

    public DownTimeBean(String endTime) {
        this.endTime = endTime;
    }

    public DownTimeBean(String endTime, long countTime) {
        this.endTime = endTime;
        this.countTime = countTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getCountTime() {
        return countTime;
    }

    public void setCountTime(long countTime) {
        this.countTime = countTime;
    }
}
