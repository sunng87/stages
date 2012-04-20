package com.tekelec.nanjing.stages;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 10:40 AM
 */
public interface Task extends Runnable {

    /**
     *
     * @param stage
     */
    public void setCurrentStage(Stage stage);

    /**
     * cancel the task before it's executed
     */
    public void cancel();

}
