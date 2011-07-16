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
        try {
            doRun();
        } catch (TaskException e) {
            if (logger != null && logger.isDebugEnabled()) {
                logger.debug("Error execute task", e);
            }
            onTaskFailure();
        } finally {
            getStage().taskComplete();
        }
    }

    protected abstract void doRun() throws TaskException ;

    protected void onTaskFailure() {}

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
