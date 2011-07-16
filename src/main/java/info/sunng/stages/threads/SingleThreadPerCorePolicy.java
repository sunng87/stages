package info.sunng.stages.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 12:07 PM
 */
public class SingleThreadPerCorePolicy extends AbstractThreadPoolPolicy {

    @Override
    public ExecutorService getThreadPool() {
        Runtime runtime = Runtime.getRuntime();
        int cores = runtime.availableProcessors();

        return Executors.newFixedThreadPool(cores, new NamedThreadFactory(getName()));
    }
}
