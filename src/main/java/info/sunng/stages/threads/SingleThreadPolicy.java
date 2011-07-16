package info.sunng.stages.threads;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 12:02 PM
 */
public class SingleThreadPolicy extends AbstractThreadPoolPolicy {

    @Override
    public ExecutorService getThreadPool() {
        return Executors.newSingleThreadExecutor(new NamedThreadFactory(getName()));
    }
}
