package info.sunng.stages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    protected static Logger logger = LoggerFactory.getLogger(AbstractTask.class);

    Stage stage;

    private TaskCallback callback = null;

    @Override
    public void run() {
        onTaskStart();
        try {
            if (logger.isTraceEnabled()) {
                logger.trace(this.getClass().getSimpleName() + "started.");
            }
            doRun();
            onTaskSuccess();
            if (logger.isTraceEnabled()) {
                logger.trace(this.getClass().getSimpleName() + "succeed.");
            }
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Error execute task", e);
            }
            onTaskFailure(new TaskException(e));
        } finally {
            getCurrentStage().taskComplete();
            onTaskFinished();
            if (logger.isTraceEnabled()) {
                logger.trace(this.getClass().getSimpleName() + "finished.");
            }
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
