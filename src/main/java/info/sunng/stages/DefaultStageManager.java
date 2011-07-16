package info.sunng.stages;

import info.sunng.stages.threads.NamedThreadFactory;
import info.sunng.stages.threads.ThreadPoolPolicy;

import java.util.Map;
import java.util.concurrent.*;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 6:23 PM
 */
public class DefaultStageManager implements StageManager {

    private ScheduledExecutorService retryScheduler
            = Executors.newScheduledThreadPool(1, new NamedThreadFactory("RetryPool"));

    private Map<String, Stage> stages = new ConcurrentHashMap<String, Stage>();

    @Override
    public void register(Stage stage) {
        stages.put(stage.getName(), stage);
    }

    @Override
    public void register(String name, ExecutorService threadPool) {
        Stage stage = new DefaultStage(name, threadPool);
        this.register(stage);
    }

    @Override
    public void register(String name, ThreadPoolPolicy threadPoolPolicy) {
        Stage stage = new DefaultStage(name, threadPoolPolicy.getThreadPool());
        this.register(stage);
    }

    @Override
    public Stage getStage(String name) {
        if (stages.containsKey(name)){
            return stages.get(name);
        } else {
            throw new IllegalStateException("No such stage: " + name);
        }
    }

    @Override
    public synchronized void start() {
        for (Stage stage: stages.values()) {
            stage.start();
        }
    }

    @Override
    public void shutdown() {
        for (Stage stage: stages.values()) {
            stage.stop();
        }
    }

    @Override
    public ScheduledExecutorService getRetrySchduler() {
        return retryScheduler;
    }
}
