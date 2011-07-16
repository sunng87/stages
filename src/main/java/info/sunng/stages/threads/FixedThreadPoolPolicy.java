package info.sunng.stages.threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 11:51 AM
 */
public class FixedThreadPoolPolicy extends AbstractThreadPoolPolicy {

    private int size;

    public FixedThreadPoolPolicy(int size) {
        this.size = size;
    }

    @Override
    public ExecutorService getThreadPool() {
        return Executors.newFixedThreadPool(size, new NamedThreadFactory(getName()));
    }
}
