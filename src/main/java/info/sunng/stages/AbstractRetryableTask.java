package info.sunng.stages;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 10:46 AM
 */
public abstract class AbstractRetryableTask extends AbstractTask{

    private boolean retry;

    private AtomicInteger executeTimes = new AtomicInteger(-1);

    public int getRetryTimes() {
        return executeTimes.intValue();
    }

    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    /**
     * override this method to specify retry delay
     *
     * @return time for next retry delay, in millseconds
     */
    protected abstract long getNextRetryDelay();

    protected abstract int getMaxRetryTimes();

    @Override
    protected void onTaskStart() {
        super.onTaskStart();
        executeTimes.incrementAndGet();
        // clear retry state
        setRetry(false);
    }

    @Override
    protected void onTaskFailure(TaskException e) {
        super.onTaskFailure(e);
        checkToRetry();
    }

    @Override
    protected void onTaskSuccess() {
        super.onTaskSuccess();
        // also check if retry is set
        checkToRetry();
    }

    private void checkToRetry() {
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
