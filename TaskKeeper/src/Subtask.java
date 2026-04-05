import java.time.LocalDate;

public class Subtask extends Task {
	protected Task parentTask;

	public Subtask(Task parentTask, String title, String description, LocalDate dueDate, Priority priority) {
		super(title, description, dueDate, priority);
		this.parentTask = parentTask;
		this.project = parentTask.getProject();
		this.tags = parentTask.getTags();
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}
	
	public String toString() {
		return super.toString() + "\n\nParent task: " + this.parentTask.getTitle();
	}

}
