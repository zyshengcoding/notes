package com.ibm.config;

public class ThreadLocalConf {

    private ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public String getThreadLocal() {
        return threadLocal.get() == null ? null : threadLocal.get();
    }

    public void setThreadLocal(String redisValue) {
        ;
        this.threadLocal.set(redisValue);
    }
}
