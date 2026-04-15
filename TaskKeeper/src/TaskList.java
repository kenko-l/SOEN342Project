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
	
	public Task createDummyTask(String title, String description, LocalDate dueDate, Priority priority) {
		return new Task(title, description, dueDate, priority);
	}
	
	public void createSubtask(Task parentTask, String title, String description, LocalDate dueDate, Priority priority) {
		Subtask newTask = new Subtask(parentTask, title, description, dueDate, priority);
		this.tasks.add(newTask);
	}
	
	public void createCollaboratorTask(Collaborator collaborator, Task parentTask, String title, String description, LocalDate dueDate, Priority priority) {
		CollaboratorTask newTask = new CollaboratorTask(collaborator, parentTask, title, description, dueDate, priority);
		this.tasks.add(newTask);
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public void createImportedSubtask(Task parentTask, String taskName, String taskDescription, LocalDate taskCreationDate, LocalDate taskDueDate, Priority taskPriority, TaskStatus taskStatus, ArrayList<Tag> tags) {
		Subtask newTask = new Subtask(parentTask, taskName, taskDescription, taskDueDate, taskPriority);
		newTask.setCreationDate(taskCreationDate);
		newTask.setStatus(taskStatus);
		newTask.setTags(tags);
		this.tasks.add(newTask);
	}

	public void createImportedCollaboratorTask(Collaborator collaborator, Task parentTask, String taskName, String taskDescription, LocalDate taskCreationDate, LocalDate taskDueDate, Priority taskPriority, TaskStatus taskStatus, ArrayList<Tag> tags) {
		CollaboratorTask newTask = new CollaboratorTask(collaborator, parentTask, taskName, taskDescription, taskDueDate, taskPriority);
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
