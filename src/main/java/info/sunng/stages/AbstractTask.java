package info.sunng.stages;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A very basic task as execution unit
 *
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 10:40 AM
 */
public abstract class AbstractTask implements Task, TaskContext {

    private Map<String, Object> taskContext = new HashMap<String, Object>();

    Logger logger;

    Stage stage;

    private TaskCallback callback = null;

    @Override
    public void run() {
        onTaskStart();
        try {
            doRun();
            onTaskSuccess();
        } catch (Exception e) {
            if (logger != null && logger.isDebugEnabled()) {
                logger.debug("Error execute task", e);
            }
            onTaskFailure(new TaskException(e));
        } finally {
            getCurrentStage().taskComplete();
            onTaskFinished();
        }
    }

    protected void onTaskFinished(){};

    protected void fireFinished() {
        if (this.callback != null) {
            this.callback.onFinished(this);
        }
    }

    public void setCallback(TaskCallback c) {
        this.callback = c;
    }

    protected abstract void doRun() throws Exception ;

    protected void onTaskFailure(TaskException e) {}

    protected void onTaskSuccess() {}

    protected void onTaskStart() {}

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Stage getCurrentStage() {
        return stage;
    }

    public void setCurrentStage(Stage stage) {
        this.stage = stage;
    }


    protected void forward(String stageName, Task task) {
        getCurrentStage().getStageManager().getStage(stageName).assign(task);
        if (task instanceof  TaskContext) {
            TaskContext taskWithContext = (TaskContext) task;
            for (String key: taskContext.keySet()) {
                taskWithContext.setAttribute(key, this.getAttribute(key, Object.class));
            }
        }
    }

    @Override
    public Collection<String> getAttributeNames() {
        return taskContext.keySet();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name, Class<T> clazz) {
        return (T)taskContext.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        taskContext.put(name, value);
    }
}
