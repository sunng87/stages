package com.tekelec.nanjing.stages.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 11:51 AM
 */
public class FixedThreadPoolPolicy extends AbstractThreadPoolPolicy {

    private int size;

    public FixedThreadPoolPolicy() {

    }

    public FixedThreadPoolPolicy(int size) {
        this.size = size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(size, size,
                0L, TimeUnit.MILLISECONDS,
                getBackedQueue(),
                new NamedThreadFactory(getName()),
                getRejectedExecutionHandler());
    }
}
