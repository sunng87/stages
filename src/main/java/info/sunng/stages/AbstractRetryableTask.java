package info.sunng.stages;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 10:46 AM
 */
public abstract class AbstractRetryableTask extends AbstractTask{

    private boolean retry;

    private int retryTimes;

    public int getRetryTimes() {
        return retryTimes;
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
    protected void onTaskFailure() {
        super.onTaskFailure();
        if (retry) {
            if (getRetryTimes() < getMaxRetryTimes()) {
                // TODO schedule a retry
            }
        }
    }
}
