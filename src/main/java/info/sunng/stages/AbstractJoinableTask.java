package info.sunng.stages;

/**
 * User: nsun2
 * Date: 4/10/12
 * Time: 4:56 PM
 */
public abstract class AbstractJoinableTask<V> extends  AbstractTask{

    private Joiner<V> joiner;

    protected void complete(V value) {
        this.joiner.join(value);
    };

}
