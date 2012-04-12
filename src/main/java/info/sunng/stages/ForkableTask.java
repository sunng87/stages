package info.sunng.stages;

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
public abstract class ForkableTask extends AbstractTask {

    private Map<String, Object> taskContext = new ConcurrentHashMap<String, Object>();

    protected AtomicInteger taskCount;

    private List<ForkedSubTask> subTaskList ;

    public ForkableTask(ForkedSubTask... tasks){
        taskCount = new AtomicInteger(tasks.length);

        subTaskList = new ArrayList<ForkedSubTask>(taskCount.intValue());
        for (ForkedSubTask subTask : tasks) {
            subTaskList.add(subTask);
        }

    }

    @Override
    protected void doRun() throws Exception {
        for (ForkedSubTask subTask : subTaskList){
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
