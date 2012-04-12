package info.sunng.stages;

import java.util.Collection;

/**
 * User: nsun2
 * Date: 4/12/12
 * Time: 3:27 PM
 */
public abstract class ForkedSubTask extends AbstractTask implements TaskContext{

    private ForkableTask parentTask;

    public void setParentTask(ForkableTask task) {
        this.parentTask = task;
    }

    public ForkableTask getParentTask() {
        return this.parentTask;
    }

    @Override
    protected void forward(String stageName, Task task) {
        throw new UnsupportedOperationException("Do not forward in forked sub task");
    }

    @Override
    protected void onTaskFinished(){
        getParentTask().join();
    };

    @Override
    public Collection<String> getAttributeNames() {
        return this.parentTask.getAttributeNames();
    }

    @Override
    public <T> T getAttribute(String name, Class<T> clazz) {
        return this.parentTask.getAttribute(name, clazz);
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.parentTask.setAttribute(name, value);
    }
}
