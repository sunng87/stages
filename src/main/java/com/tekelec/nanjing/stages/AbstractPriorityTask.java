package com.tekelec.nanjing.stages;

/**
 * User: nsun
 * Date: 7/11/12
 * Time: 7:53 AM
 */
public abstract class AbstractPriorityTask extends AbstractTask
        implements Comparable<AbstractPriorityTask> {

    public int priority ;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(AbstractPriorityTask that) {
        return this.getPriority() > that.getPriority() ?
                1 : (this.getPriority() < that.getPriority() ? -1 :0);
    }
}
