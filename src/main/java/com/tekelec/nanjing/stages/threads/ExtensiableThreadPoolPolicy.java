package com.tekelec.nanjing.stages.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 12:11 PM
 */
public class ExtensiableThreadPoolPolicy extends AbstractThreadPoolPolicy {

    private int coreSize;

    private int maxSize;

    private int timeout;

    public ExtensiableThreadPoolPolicy(int coreSize, int maxSize) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
    }

    public ExtensiableThreadPoolPolicy(int coreSize, int maxSize, int timeout) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.timeout = timeout;
    }

    @Override
    public ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(coreSize, maxSize, timeout, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory(getName()));
    }
}
