package com.tekelec.nanjing.stages;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 7:59 PM
 */
public class RetryTaskWrapper implements Runnable {

    private AbstractRetryableTask task;

    public RetryTaskWrapper(AbstractRetryableTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.getCurrentStage().assign(task);
    }
}
