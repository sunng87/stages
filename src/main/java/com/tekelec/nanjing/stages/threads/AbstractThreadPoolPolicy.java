package com.tekelec.nanjing.stages.threads;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 12:05 PM
 */
public abstract class AbstractThreadPoolPolicy implements ThreadPoolPolicy{

    private String name;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
