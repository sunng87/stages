package info.sunng.stages;

import info.sunng.stages.threads.ThreadPoolPolicy;

import java.util.concurrent.ExecutorService;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 1:38 PM
 */
public interface StageManager {

    public void register(Stage stage);

    public void register(String name, ExecutorService threadPool);

    public void register(String name, ThreadPoolPolicy threadPoolPolicy);

    public Stage getStage(String name);

    public void start();

    public void shutdown();

}
