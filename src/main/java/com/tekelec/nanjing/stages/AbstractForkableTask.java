package com.tekelec.nanjing.stages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: nsun2
 * Date: 4/12/12
 * Time: 3:26 PM
 */
public abstract class AbstractForkableTask extends AbstractTask {

    private Map<String, Object> taskContext = new ConcurrentHashMap<String, Object>();

    protected AtomicInteger taskCount;

    private List<AbstractForkedSubTask> subTaskList ;

    public AbstractForkableTask(AbstractForkedSubTask... tasks){
        taskCount = new AtomicInteger(tasks.length);

        subTaskList = new ArrayList<AbstractForkedSubTask>(taskCount.intValue());
        for (AbstractForkedSubTask subTask : tasks) {
            subTask.setParentTask(this);
            subTaskList.add(subTask);
        }

    }

    public AbstractForkableTask(List<AbstractForkedSubTask> tasks) {
        this.subTaskList = tasks;
        this.taskCount = new AtomicInteger(tasks.size());
    }

    @Override
    protected void doRun() throws Exception {
        for (AbstractForkedSubTask subTask : subTaskList){
            getCurrentStage().assign(subTask);
        }
    }

    protected abstract void onSubTasksComplete();

    public void join(){
        if (taskCount.decrementAndGet() == 0) {
            this.onSubTasksComplete();
        }
    }

}
