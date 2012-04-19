package com.tekelec.nanjing.stages;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: nsun2
 * Date: 4/9/12
 * Time: 2:59 PM
 */
public class Promise<T> {

    T value = null;

    private CountDownLatch latch =  new CountDownLatch(1);

    /**
     * block until value is supplied
     *
     * @return
     */
    public T get() throws InterruptedException {
        latch.await();
        return value;
    }
    
    public T get(long timeout, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        boolean result = latch.await(timeout, timeUnit);
        if (!result) {
            throw new TimeoutException();
        } else {
            return value;
        }
    }

    public synchronized void deliver(T value) {
        if (!isRealized()) {
            this.value = value;
            latch.countDown();
        } else {
            throw new IllegalStateException("This promise is already realized");
        }
    }

    public boolean isRealized() {
        return latch.getCount() > 0;
    }

}
