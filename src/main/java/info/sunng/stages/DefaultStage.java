package info.sunng.stages;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 11:09 AM
 */
public class DefaultStage implements Stage {

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

    public AtomicInteger getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(AtomicInteger taskCount) {
        this.taskCount = taskCount;
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
        getThreadPool().shutdownNow();
    }

    @Override
    public void assign(Task t) {
        if (t == null) {
            return ;
        }

        if (! this.isStarted()) {
            throw new IllegalStateException("Stage " + name + " is inactive.");
        }

        taskCount.incrementAndGet();
        t.setCurrentStage(this);

        getThreadPool().submit(t);

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int pendingTasks() {
        return getTaskCount().intValue();
    }

    @Override
    public void taskComplete() {
        taskCount.decrementAndGet();
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
