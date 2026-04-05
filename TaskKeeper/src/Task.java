import java.time.*;
import java.util.*;

public class Task {
	protected String title;
	protected String description;
	protected LocalDate creationDate;
	protected LocalDate dueDate;
	protected Priority priority;
	protected TaskStatus status;
	protected ArrayList<Tag> tags;
	protected Project project;
	
	public Task(String title, String description, LocalDate dueDate, Priority priority) {
		this.title = title;
		this.description = description;
		this.creationDate = LocalDate.now();
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = TaskStatus.OPEN;
		this.project = null;
		this.tags = new ArrayList<Tag>();
	}
	
	
	@Override
	public String toString() {		
		String output =	("Title: " + this.title
				+ "\nDescription: " + this.description
				+ "\n\nCreated on: " + this.creationDate
				);
		if (project != null) output += ("\nProject: " + this.project.getName());		
		
		if (dueDate != null) output += ("\nDue date: " + this.dueDate);
		
		output += ("\n\nPriority: " + this.priority
				+ "\nStatus: " + this.status
				);
		
		if (tags.size() > 0) {
			output += ("\n\nTags: " );
			for (Tag tag : tags) {
				output += (tag + ", ");
			}
		}
		return output;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public void setPriority(String priority) {
		switch(priority) {
		case "1":
			this.priority = Priority.LOW;
			break;
		case "2":
			this.priority = Priority.MEDIUM;
			break;
		case "3":
			this.priority = Priority.HIGH;
			break;
		}
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	public void setStatus(String status) {
		switch(status) {
		case "1":
			this.status = TaskStatus.OPEN;
			break;
		case "2":
			this.status = TaskStatus.COMPLETED;
			break;
		case "3":
			this.status = TaskStatus.CANCELLED;
			break;
		}
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
