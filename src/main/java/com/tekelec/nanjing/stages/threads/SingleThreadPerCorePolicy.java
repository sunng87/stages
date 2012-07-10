package com.tekelec.nanjing.stages.threads;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 12:07 PM
 */
public class SingleThreadPerCorePolicy extends FixedThreadPoolPolicy {

    public SingleThreadPerCorePolicy() {
        Runtime runtime = Runtime.getRuntime();
        int cores = runtime.availableProcessors();

        setSize(cores);
    }

}
