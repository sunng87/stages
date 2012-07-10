package com.tekelec.nanjing.stages.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 12:05 PM
 */
public abstract class AbstractThreadPoolPolicy implements ThreadPoolPolicy{

    private RejectedExecutionHandler rejectedExecutionHandler
            = new ThreadPoolExecutor.AbortPolicy();

    private BlockingQueue<Runnable> backedQueue = new LinkedBlockingQueue<Runnable>();

    private String name;

    @Override
    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }

    @Override
    public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }

    @Override
    public void setBackedQueue(BlockingQueue<Runnable> backedQueue) {
        this.backedQueue = backedQueue;
    }

    @Override
    public BlockingQueue<Runnable> getBackedQueue() {
        return this.backedQueue;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
