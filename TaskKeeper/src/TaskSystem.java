import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TaskSystem {
	private TaskList taskList;
	private ProjectList projectList;
	private TagList tagList;
	private ActivityEntryList activityEntryList;
	
	public TaskSystem() {
		this.taskList = new TaskList();
		this.projectList = new ProjectList();
		this.tagList = new TagList();
		this.activityEntryList = new ActivityEntryList();
	}
	
	//-----------------------------------------------------------------------
	//Task functions
	//-----------------------------------------------------------------------
	
	public void createTask(String taskName, String taskDescription, String taskPriority, String taskDueDate) {
		Priority formattedTaskPriority = null;
		switch(taskPriority) {
		case "1":
			formattedTaskPriority = Priority.LOW;
			break;
		case "2":
			formattedTaskPriority = Priority.MEDIUM;
			break;
		case "3":
			formattedTaskPriority = Priority.HIGH;
			break;
		}
		
		LocalDate formattedDueDate = null;
		if (!taskDueDate.equals("")) formattedDueDate = LocalDate.parse(taskDueDate);
		
		taskList.createTask(taskName, taskDescription, formattedDueDate, formattedTaskPriority);
	}
	
	public void createSubtask(Task parentTask, String taskName, String taskDescription, String taskPriority, String taskDueDate) {
		Priority formattedTaskPriority = null;
		switch(taskPriority) {
		case "1":
			formattedTaskPriority = Priority.LOW;
			break;
		case "2":
			formattedTaskPriority = Priority.MEDIUM;
			break;
		case "3":
			formattedTaskPriority = Priority.HIGH;
			break;
		}
		
		LocalDate formattedDueDate = null;
		if (!taskDueDate.equals("")) formattedDueDate = LocalDate.parse(taskDueDate);
		
		taskList.createSubtask(parentTask, taskName, taskDescription, formattedDueDate, formattedTaskPriority);
	}
	
	public void createCollaboratorTask(Collaborator collaborator, Task parentTask, String taskName, String taskDescription, String taskPriority, String taskDueDate) {
		Priority formattedTaskPriority = null;
		switch(taskPriority) {
		case "1":
			formattedTaskPriority = Priority.LOW;
			break;
		case "2":
			formattedTaskPriority = Priority.MEDIUM;
			break;
		case "3":
			formattedTaskPriority = Priority.HIGH;
			break;
		}
		
		LocalDate formattedDueDate = null;
		if (!taskDueDate.equals("")) formattedDueDate = LocalDate.parse(taskDueDate);
		
		taskList.createCollaboratorTask(collaborator, parentTask, taskName, taskDescription, formattedDueDate, formattedTaskPriority);
	}
	
	public void createImportedTask(String taskName, String taskDescription, String taskCreationDate, String taskDueDate, String taskPriority, String taskStatus, String parentTask, ArrayList<Tag> tags) {
		if (getTaskByName(taskName) != null) {
			System.out.println("Could not import task '" + taskName + "': Task name already in use");
			return;
		}
		
		LocalDate formattedCreationDate = LocalDate.parse(taskCreationDate);
		LocalDate formattedDueDate = LocalDate.parse(taskDueDate);
		Priority formattedTaskPriority = null;
		switch(taskPriority) {
		case "LOW":
			formattedTaskPriority = Priority.LOW;
			break;
		case "MEDIUM":
			formattedTaskPriority = Priority.MEDIUM;
			break;
		case "HIGH":
			formattedTaskPriority = Priority.HIGH;
			break;
		}
		TaskStatus formmatedStatus = null;
		switch(taskStatus) {
		case "OPEN":
			formmatedStatus = TaskStatus.OPEN;
			break;
		case "COMPLETED":
			formmatedStatus = TaskStatus.COMPLETED;
			break;
		case "CANCELED":
			formmatedStatus = TaskStatus.CANCELLED;
			break;
		}
		Task formattedParentTask = getTaskByName(parentTask);
		
		taskList.createImportedTask(taskName, taskDescription, formattedCreationDate, formattedDueDate, formattedTaskPriority, formmatedStatus, formattedParentTask, tags);
	}
	
	public ArrayList<Task> getTasks(String filterType, String filterPackage, String sortType, String sortOrder) {
		ArrayList<Task> taskArray = new ArrayList<Task>(this.taskList.getTasks());
		
		if (filterType.equals("Keyword")) {
			for (int i = 0; i < taskArray.size(); i++) {
				Task task = taskArray.get(i);
				boolean inTitle = task.getTitle() != null && task.getTitle().toLowerCase().contains(filterPackage.toLowerCase());
				boolean inDescription = task.getDescription() != null && task.getDescription().toLowerCase().contains(filterPackage.toLowerCase());
				
				if (!inTitle && !inDescription) {
					taskArray.remove(i);
					i--;
				}
			}
		}
		
		else if (filterType.equals("Priority")) {
			for (int i = 0; i < taskArray.size(); i++) {
				if (!taskArray.get(i).getPriority().toString().equalsIgnoreCase(filterPackage)) {
					taskArray.remove(i);
					i--;
				}
			}
		}
		
		else if (filterType.equals("Status")) {
			for (int i = 0; i < taskArray.size(); i++) {
				if (!taskArray.get(i).getStatus().toString().equalsIgnoreCase(filterPackage)) {
					taskArray.remove(i);
					i--;
				}
			}
		}
		
		else if (filterType.equals("Project")) {
			for (int i = 0; i < taskArray.size(); i++) {
				Task task = taskArray.get(i);
				if (task.getProject() == null || !task.getProject().getName().equalsIgnoreCase(filterPackage)) {
					taskArray.remove(i);
					i--;
				}
			}
		}
		
		else if (filterType.equals("Date range")) {
			String[] dates = filterPackage.split(",");
			LocalDate startDate = LocalDate.parse(dates[0]);
			LocalDate endDate = LocalDate.parse(dates[1]);
			
			for (int i = 0; i < taskArray.size(); i++) {
				Task task = taskArray.get(i);
				LocalDate dueDate = task.getDueDate();
				
				if (dueDate == null || dueDate.isBefore(startDate) || dueDate.isAfter(endDate)) {
					taskArray.remove(i);
					i--;
				}
			}
		}
		
		else if (filterType.equals("Tag")) {
			for (int i = 0; i < taskArray.size(); i++) {
				Task task = taskArray.get(i);
				boolean hasTag = false;
				
				for (int j = 0; j < task.getTags().size(); j++) {
					if (task.getTags().get(j).getTitle().equalsIgnoreCase(filterPackage)) {
						hasTag = true;
						break;
					}
				}
				
				if (!hasTag) {
					taskArray.remove(i);
					i--;
				}
			}
		}
		
		if (sortType.equals("Due date")) {
			Collections.sort(taskArray, new Comparator<Task>() {
				public int compare(Task t1, Task t2) {
					LocalDate d1 = t1.getDueDate();
					LocalDate d2 = t2.getDueDate();
					
					if (d1 == null && d2 == null) return 0;
					if (d1 == null) return 1;
					if (d2 == null) return -1;
					return d1.compareTo(d2);
				}
			});
		}
		
		else if (sortType.equals("Priority")) {
			Collections.sort(taskArray, new Comparator<Task>() {
				public int compare(Task t1, Task t2) {
					int p1 = getPriorityValue(t1.getPriority().toString());
					int p2 = getPriorityValue(t2.getPriority().toString());
					return Integer.compare(p1, p2);
				}
			});
		}
		
		if (sortOrder.equals("Descending")) Collections.reverse(taskArray);
		
		return taskArray;
	}

	private int getPriorityValue(String priority) {
		if (priority.equalsIgnoreCase("Low")) return 1;
		if (priority.equalsIgnoreCase("Medium")) return 2;
		if (priority.equalsIgnoreCase("High")) return 3;
		return 0;
	}
	
	public Task getTaskByName(String taskName) {
		for (Task task : taskList.getTasks()) {
			if (task.getTitle().equalsIgnoreCase(taskName)) return task;
		}
		return null;
	}
	
	public ArrayList<Subtask> getSubtasks(){
		return taskList.getSubtasks();
	}
	
	public ArrayList<CollaboratorTask> getCollaboratorTasks(){
		return taskList.getCollaboratorTasks();
	}
	
	public ArrayList<Subtask> getTaskSubtasks(Task task){
		ArrayList<Subtask> output = new ArrayList<Subtask>();
		ArrayList<Subtask> subtasks = getSubtasks();
		for (Subtask subtask: subtasks) {
			if (subtask.getParentTask().equals(task)) {
				output.add(subtask);
			}
		}
		return output;
	}
	
	public ArrayList<CollaboratorTask> getTaskCollaboratorTasks(Task task){
		ArrayList<CollaboratorTask> output = getCollaboratorTasks();
		for (CollaboratorTask collaboratorTask: output) {
			if (!collaboratorTask.getParentTask().equals(task)) {
				output.remove(collaboratorTask);
			}
		}
		return output;
	}
	
	public void createActivityEntry(Task task, String description) {
		LocalDateTime timestamp = LocalDateTime.now();
		this.activityEntryList.createActivityEntry(task, description, timestamp);
	}
	
	public ArrayList<ActivityEntry> getActivity(Task task){
		ArrayList<ActivityEntry> output = new ArrayList<ActivityEntry>();
		for (ActivityEntry activity : this.activityEntryList.getActivityEntryList()) {
			if (activity.getTask().getTitle().equalsIgnoreCase(task.getTitle())) output.add(activity);
		}
		return output;
	}
	
	//---------------------------------------------------------------
	//Tag section
	//---------------------------------------------------------------
	public ArrayList<Tag> getTags() {
		return this.tagList.getTags();
	}
	
	public void createTag(String title) {
		tagList.createTag(title);
	}
	
	public Tag getTagByName(String tagName) {
		for (Tag tag : tagList.getTags()) {
			if (tag.getTitle().equalsIgnoreCase(tagName)) return tag;
		}
		return null;
	}
	
	public void addTagToTask(Task task, String tagName) {
		Tag tag = getTagByName(tagName);
		task.getTags().add(tag);
	}
	
	public void removeTagFromTask(Task task, String tagName) {
		Tag tag = getTagByName(tagName);
		task.getTags().remove(tag);
	}
	
	//---------------------------------------------------------------
	//Project section
	//---------------------------------------------------------------
	
	public ArrayList<Project> getProjects() {
		return this.projectList.getProjects();
	}
	
	public void createProject(String title, String description) {
		projectList.createProject(title, description);
	}
	
	public Project getProjectByName(String projectName) {
		for (Project project : projectList.getProjects()) {
			if (project.getName().equalsIgnoreCase(projectName)) return project;
		}
		return null;
	}
	
	public void updateTaskProject(Task task, String projectName) {
		Project project = getProjectByName(projectName);
		task.setProject(project);
	}
	
	public Collaborator getCollaboratorByName(Project project, String targetCollaborator) {
		for (Collaborator collaborator : project.getCollaborators()) {
			if (collaborator.getName().equalsIgnoreCase(targetCollaborator)) return collaborator;
		}
		return null;
	}
	
	public void createCollaborator(String name, String category, Project project) {
		Category formattedCategory = null;
		switch(category) {
		case "1":
			formattedCategory = Category.JUNIOR;
		case "2":
			formattedCategory = Category.INTERMEDIATE;
		case "3":
			formattedCategory = Category.SENIOR;
		}
		project.addCollaborator(new Collaborator(name, formattedCategory));
	}
	
	public boolean isAtCapacity(Project project, Collaborator collaborator) {
		ArrayList<Task> openTasks = new ArrayList<Task>();
		for (CollaboratorTask collaboratorTask : getCollaboratorTasks()) {
			if (collaboratorTask.getCollaborator().equals(collaborator) &&
				collaboratorTask.getStatus() == TaskStatus.OPEN) openTasks.add(collaboratorTask);
		}
		switch (collaborator.getCategory()) {
		case JUNIOR:
			return openTasks.size() > 9 ? true : false;
		case INTERMEDIATE:
			return openTasks.size() > 4 ? true : false;
		case SENIOR:
			return openTasks.size() > 1 ? true : false;
		default:
			return true;
		}
	}
	
	public boolean isOverloaded(Project project, Collaborator collaborator) {
		ArrayList<Task> openTasks = new ArrayList<Task>();
		for (CollaboratorTask collaboratorTask : getCollaboratorTasks()) {
			if (collaboratorTask.getCollaborator().equals(collaborator) &&
				collaboratorTask.getStatus() == TaskStatus.OPEN) openTasks.add(collaboratorTask);
		}
		switch (collaborator.getCategory()) {
		case JUNIOR:
			return openTasks.size() > 10 ? true : false;
		case INTERMEDIATE:
			return openTasks.size() > 5 ? true : false;
		case SENIOR:
			return openTasks.size() > 2 ? true : false;
		default:
			return true;
		}
	}
	
	//---------------------------------------------------------------
	//Comparators
	//---------------------------------------------------------------
	public static Comparator<Task> byDueDate() {
	    return (t1, t2) -> {
	        if (t1.getDueDate() == null && t2.getDueDate() == null) return 0;
	        if (t1.getDueDate() == null) return 1;  // null last
	        if (t2.getDueDate() == null) return -1;
	        return t1.getDueDate().compareTo(t2.getDueDate());
	    };
	}
	
	public static Comparator<Task> byPriority() {
	    return (t1, t2) -> {
	        return Integer.compare(
	            priorityValue(t1.getPriority()),
	            priorityValue(t2.getPriority())
	        );
	    };
	}

	private static int priorityValue(Priority p) {
	    switch (p) {
	        case HIGH: return 0;
	        case MEDIUM: return 1;
	        case LOW: return 2;
	        default: return 3;
	    }
	}
	
	public static Comparator<Task> byStatus() {
	    return (t1, t2) -> {
	        return Integer.compare(
	            statusValue(t1.getStatus()),
	            statusValue(t2.getStatus())
	        );
	    };
	}

	private static int statusValue(TaskStatus s) {
	    switch (s) {
	        case OPEN: return 0;
	        case COMPLETED: return 1;
	        case CANCELLED: return 2;
	        default: return 3;
	    }
	}
	//---------------------------------------------------
	//Import/Export
	//---------------------------------------------------
	 
}
