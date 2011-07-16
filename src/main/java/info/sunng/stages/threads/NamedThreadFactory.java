package info.sunng.stages.threads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 11:35 AM
 */
public class NamedThreadFactory implements ThreadFactory {

    private String name;

    private AtomicInteger counter = new AtomicInteger(0);

    public NamedThreadFactory(String name){
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(name+"-"+counter.incrementAndGet());
        t.setDaemon(true);

        return t;
    }
}
