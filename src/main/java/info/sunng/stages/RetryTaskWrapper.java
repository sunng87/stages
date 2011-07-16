package info.sunng.stages;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 7:59 PM
 */
public class RetryTaskWrapper implements Runnable {

    private AbstractRetryableTask task;

    public RetryTaskWrapper(AbstractRetryableTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.getCurrentStage().assign(task);
    }
}
