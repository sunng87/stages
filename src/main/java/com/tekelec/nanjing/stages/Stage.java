package com.tekelec.nanjing.stages;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 10:37 AM
 */
public interface Stage {

    /**
     * initilize
     */
    public void init();

    /**
     * start
     */
    public void start();

    /**
     * shutdown this stage
     */
    public void stop();

    /**
     * assign a task to the stage
     * @param t
     */
    public void assign(Task t);

    /**
     * get name of the stage
     * @return
     */
    public String getName();

    /**
     * get the number of pending tasks
     * @return
     */
    public int pendingTasks();

    /**
     * call this when task complete
     */
    public void taskComplete();

    /**
     *
     * @return
     */
    public StageManager getStageManager();

    /**
     *
     * @param manager
     */
    public void setStageManager(StageManager manager);
}
