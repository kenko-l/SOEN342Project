import java.util.ArrayList;

public class TaskList {
	private ArrayList<Task> tasks;
	
	public TaskList() {
		this.tasks = new ArrayList<Task>();
	}
	
	public void createTask(String title, String description, String dueDate, String priority) {
		switch(priority) {
		case "1":
			priority = "Low";
			break;
		case "2":
			priority = "Medium";
			break;
		case "3":
			priority = "High";
			break;
		}
		Task newTask = new Task(title, description, dueDate, priority);
		this.tasks.add(newTask);
	}
	
	public ArrayList<Task> getTasks(){
		return this.tasks;
	}
}
