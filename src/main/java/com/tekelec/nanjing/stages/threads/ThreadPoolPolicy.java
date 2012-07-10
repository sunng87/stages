package com.tekelec.nanjing.stages.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 11:50 AM
 */
public interface ThreadPoolPolicy {

    public void setName(String name);

    public ExecutorService getThreadPool();

    public RejectedExecutionHandler getRejectedExecutionHandler();

    public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler);

    public abstract void setBackedQueue(BlockingQueue<Runnable> backedQueue);

    public abstract BlockingQueue<Runnable> getBackedQueue();

}
