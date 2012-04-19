package com.tekelec.nanjing.stages.threads;

import java.util.concurrent.ExecutorService;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 11:50 AM
 */
public interface ThreadPoolPolicy {

    public void setName(String name);

    public ExecutorService getThreadPool();

}
