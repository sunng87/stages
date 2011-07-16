package info.sunng.stages.threads;

import java.util.concurrent.ThreadFactory;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 11:35 AM
 */
public class NamedThreadFactory implements ThreadFactory {

    private String name;

    public NamedThreadFactory(String name){
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread();
        t.setName(name);

        return t;
    }
}
