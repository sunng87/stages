package info.sunng.stages;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: nsun2
 * Date: 4/10/12
 * Time: 4:46 PM
 */
public abstract class Joiner<V> {

    private List<V> values = Collections.synchronizedList(new LinkedList<V>());

    private AtomicInteger subTaskCount = null;

    public Joiner(int subTaskCount) {
        this.subTaskCount = new AtomicInteger(subTaskCount);
    }

    //TODO timeout

    public void join(V value){
        values.add(value);
        if (this.subTaskCount.decrementAndGet() == 0) {
            this.onComplete();
        }
    };

    public abstract void onComplete() ;

}
