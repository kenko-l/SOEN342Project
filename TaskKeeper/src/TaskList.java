import java.util.ArrayList;
import java.time.*;

public class TaskList {
	private ArrayList<Task> tasks;
	
	public TaskList() {
		this.tasks = new ArrayList<Task>();
	}
	
	public void createTask(String title, String description, LocalDate dueDate, Priority priority) {
		Task newTask = new Task(title, description, dueDate, priority);
		this.tasks.add(newTask);
	}
	
	public void createSubtask(Task parentTask, String title, String description, LocalDate dueDate, Priority priority) {
		Subtask newTask = new Subtask(parentTask, title, description, dueDate, priority);
		this.tasks.add(newTask);
	}
	
	public void createCollaboratorTask(Collaborator collaborator, Task parentTask, String title, String description, LocalDate dueDate, Priority priority) {
		CollaboratorTask newTask = new CollaboratorTask(collaborator, parentTask, title, description, dueDate, priority);
		this.tasks.add(newTask);
	}
	
	public void createImportedTask(String taskName, String taskDescription, LocalDate taskCreationDate, LocalDate taskDueDate, Priority taskPriority, TaskStatus taskStatus, Task parentTask, ArrayList<Tag> tags) {
		Task newTask = new Task(taskName, taskDescription, taskDueDate, taskPriority);
		newTask.setCreationDate(taskCreationDate);
		newTask.setStatus(taskStatus);
		newTask.setTags(tags);
		this.tasks.add(newTask);
	}
	
	public ArrayList<Task> getTasks(){
		return this.tasks;
	}
	
	public ArrayList<Subtask> getSubtasks(){
		ArrayList<Subtask> output = new ArrayList<Subtask>();
		for (Task task : getTasks()) {
			if (task instanceof Subtask) output.add((Subtask) task);
		}
		return output;
	}
	
	public ArrayList<CollaboratorTask> getCollaboratorTasks(){
		ArrayList<CollaboratorTask> output = new ArrayList<CollaboratorTask>();
		for (Task task : getTasks()) {
			if (task instanceof CollaboratorTask) output.add((CollaboratorTask) task);
		}
		return output;
	}
	
}
