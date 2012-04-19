package com.tekelec.nanjing.stages;

import com.tekelec.nanjing.stages.threads.ThreadPoolPolicy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 1:38 PM
 */
public interface StageManager {

    public void register(Stage stage);

    public void register(String name, ExecutorService threadPool);

    public void register(String name, ThreadPoolPolicy threadPoolPolicy);

    public Stage getStage(String name);

    public List<Stage> getStages();

    public void start();

    public void shutdown();

    public ScheduledExecutorService getRetrySchduler();

    public boolean isStarted();

}
