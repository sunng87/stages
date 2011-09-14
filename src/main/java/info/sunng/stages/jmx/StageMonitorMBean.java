package info.sunng.stages.jmx;

/**
 *
 *
 */
public interface StageMonitorMBean {

	public String[] getStages() ;

	public int getPendingTasks(String stage);

}
