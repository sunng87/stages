package info.sunng.stages;

import info.sunng.stages.threads.NamedThreadFactory;
import info.sunng.stages.threads.ThreadPoolPolicy;

import java.util.ArrayList;
import java.util.List;
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

    private boolean started;

    @Override
    public void register(Stage stage) {
        stages.put(stage.getName(), stage);
        stage.setStageManager(this);
        stage.init();
    }

    @Override
    public void register(String name, ExecutorService threadPool) {
        Stage stage = new DefaultStage(name, threadPool);
        this.register(stage);
    }

    @Override
    public void register(String name, ThreadPoolPolicy threadPoolPolicy) {
        threadPoolPolicy.setName(name);
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
    public List<Stage> getStages() {
        return new ArrayList<Stage>(stages.values());
    }

    @Override
    public synchronized void start() {
        for (Stage stage: stages.values()) {
            stage.start();
        }
        started = true;
    }

    @Override
    public synchronized void shutdown() {
        for (Stage stage: stages.values()) {
            stage.stop();
        }
        stages.clear();
        started = false;
    }

    @Override
    public ScheduledExecutorService getRetrySchduler() {
        return retryScheduler;
    }

    @Override
    public boolean isStarted() {
        return started;
    }
}
