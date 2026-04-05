import java.time.*;

public class ActivityEntry {
	private Task task;
	private LocalDateTime timestamp;
	private String description;
	
	public ActivityEntry(Task task, LocalDateTime timestamp, String description) {
		this.task = task;
		this.timestamp = timestamp;
		this.description = description;
	}
	
	public String toString() {
		return (timestamp + ": " + description);
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
