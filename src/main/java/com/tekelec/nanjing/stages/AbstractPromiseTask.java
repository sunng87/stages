package com.tekelec.nanjing.stages;

/**
 * User: nsun2
 * Date: 4/9/12
 * Time: 3:51 PM
 */
public abstract class AbstractPromiseTask<T> extends AbstractTask {

    private Promise<T> promise = null ;

    public Promise getPromise() {
        return promise;
    }

    public void setPromise(Promise promise) {
        this.promise = promise;
    }

    protected void deliver(T value) {
        if (promise != null) {
            promise.deliver(value);
        }
    }

    @Override
    protected void forward(String stageName, Task task) {
        if (!(task instanceof AbstractPromiseTask)) {
            throw new IllegalStateException("If you forward to a non-AbstractPromiseTask, your promise will lost.");
        }

        super.forward(stageName, task);
        ((AbstractPromiseTask)task).setPromise(promise);
    }

}
