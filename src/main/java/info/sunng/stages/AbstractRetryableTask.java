package info.sunng.stages;

import java.util.concurrent.TimeUnit;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 10:46 AM
 */
public abstract class AbstractRetryableTask extends AbstractTask{

    private boolean retry;

    private int executeTimes = 0;

    public int getRetryTimes() {
        return executeTimes - 1;
    }

    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    protected abstract int getNextRetryDelay();

    protected abstract int getMaxRetryTimes();

    @Override
    protected void onTaskStart() {
        super.onTaskStart();
        if (isRetry()) {
            executeTimes++;
        }
    }

    @Override
    protected void onTaskFailure(TaskException e) {
        super.onTaskFailure(e);
        if (isRetry()) {
            if (getRetryTimes() < getMaxRetryTimes()) {
                getCurrentStage().getStageManager().getRetrySchduler()
                        .schedule(new RetryTaskWrapper(this),
                                getNextRetryDelay(), TimeUnit.MILLISECONDS);
            } else {
                if (logger != null && logger.isDebugEnabled()) {
                    logger.debug("Max retry exceeds, {0}", this);
                }
                onMaxRetryExceed();
            }
        }
    }

    protected void onMaxRetryExceed() {}
}
