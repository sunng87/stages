package info.sunng.stages;

import org.slf4j.Logger;

/**
 * A very basic task as execution unit
 *
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 10:40 AM
 */
public abstract class AbstractTask implements Task{

    Logger logger;

    Stage stage;

    @Override
    public void run() {
        onTaskStart();
        try {
            doRun();
            onTaskSuccess();
        } catch (TaskException e) {
            if (logger != null && logger.isDebugEnabled()) {
                logger.debug("Error execute task", e);
            }
            onTaskFailure(e);
        } finally {
            getCurrentStage().taskComplete();
        }
    }

    protected abstract void doRun() throws TaskException ;

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
    }
}
