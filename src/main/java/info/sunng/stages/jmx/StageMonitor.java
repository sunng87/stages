package info.sunng.stages.jmx;

import info.sunng.stages.Stage;
import info.sunng.stages.StageManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StageMonitor implements StageMonitorMBean{

	private StageManager sm;

	public StageMonitor (StageManager sm) {
		this.sm = sm;
	}

	@Override
	public String[] getStages() {
		List<String> names = new ArrayList<String>();
		for (Stage s: this.sm.getStages()) {
			names.add(s.getName());
		}
		return names.toArray(new String[0]);
	}

	@Override
	public int getPendingTasks(String stage) {
		return this.sm.getStage(stage).pendingTasks();
	}
}
