import java.time.*;
import java.util.*;

public class Task {
	private String title;
	private String description;
	private LocalDate creationDate;
	private LocalDate dueDate;
	private String priority;
	private String status;
	private ArrayList<Task> subTasks;
	
	public Task(String title, String description, String dueDate, String priority) {
		this.title = title;
		this.description = description;
		this.creationDate = LocalDate.now();
		if (dueDate.length()>0) this.dueDate = LocalDate.parse(dueDate);
		else this.dueDate = null;
		this.priority = priority;
		this.status = "Open";
		this.subTasks = new ArrayList<Task>();
	}
	
	@Override
	public String toString() {
		
		String output =	("Title: " + this.title
				+ "\nDescription: " + this.description
				+ "\n\nCreated on: " + this.creationDate
				);
				
		if (dueDate != null) output += ("\nDue date: " + this.dueDate);
		
		output += ("\n\nPriority: " + this.priority
				+ "\nStatus: " + this.status
				);
		
		if (this.subTasks.size() > 0) {
			output += "\n\nSubtasks:";
			for (int i = 0; i<subTasks.size(); i++) {
				output += "\n" + (i+1) + ": " + subTasks.get(i).title;
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Task> getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(ArrayList<Task> subTasks) {
		this.subTasks = subTasks;
	}
	
	
}
