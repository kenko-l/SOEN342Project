import java.util.*;
import java.time.*;

public class ActivityEntryList {
	private ArrayList<ActivityEntry> activityEntryList;
	
	public ActivityEntryList() {
		this.activityEntryList = new ArrayList<ActivityEntry>();
	}
	
	public void createActivityEntry(Task task, String description, LocalDateTime timestamp) {
		ActivityEntry newEntry = new ActivityEntry(task, timestamp, description);
		this.activityEntryList.add(newEntry);
	}
	
	public ArrayList<ActivityEntry> getActivityEntryList() {
		return this.activityEntryList;
	}
}
