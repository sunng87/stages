package com.tekelec.nanjing.stages.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 12:02 PM
 */
public class SingleThreadPolicy extends AbstractThreadPoolPolicy {

    @Override
    public ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                getBackedQueue(), new NamedThreadFactory(getName()),
                getRejectedExecutionHandler());
    }
}
