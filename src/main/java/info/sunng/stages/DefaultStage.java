package info.sunng.stages;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 11:09 AM
 */
public class DefaultStage implements Stage {
    
    private static Logger logger = LoggerFactory.getLogger(DefaultStage.class);

    private StageManager stageManager;

    private boolean started;

    private String name;

    private ExecutorService threadPool;

    private AtomicInteger taskCount = new AtomicInteger(0);

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public DefaultStage(String name, ExecutorService threadPool) {
        this.name = name ;
        this.threadPool = threadPool;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * Override this for your custom initialization
     */
    @Override
    public void init() {}

    @Override
    public synchronized void start() {
        if (!this.isStarted()) {
            this.setStarted(true);
        }

    }

    @Override
    public synchronized void stop() {
        getThreadPool().shutdown();
    }

    @Override
    public void assign(Task t) {
        if (t == null) {
            return ;
        }

        if (! this.isStarted()) {
            throw new IllegalStateException("Stage " + name + " is inactive.");
        }

        taskCount.getAndIncrement();
        if (logger.isTraceEnabled()) {
            logger.trace("Stage {} pending tasks: {}",
                    name, taskCount.intValue());
        }

        t.setCurrentStage(this);

        getThreadPool().submit(t);

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int pendingTasks() {
        return taskCount.intValue();
    }

    @Override
    public void taskComplete() {
        taskCount.getAndDecrement();
    }

    @Override
    public StageManager getStageManager() {
        return stageManager;
    }

    @Override
    public void setStageManager(StageManager manager) {
        this.stageManager = manager;
    }
}
