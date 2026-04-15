import java.io.*;
import java.nio.file.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TaskSystem {
	private TaskList taskList;
	private ProjectList projectList;
	private TagList tagList;
	private ActivityEntryList activityEntryList;
	private ArrayList<RecurrenceRule> recurrenceRules;
	
	public TaskSystem() {
		this.taskList = new TaskList();
		this.projectList = new ProjectList();
		this.tagList = new TagList();
		this.activityEntryList = new ActivityEntryList();
		this.recurrenceRules = new ArrayList<RecurrenceRule>();
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
	
	public Task createDummyTask(String taskName, String taskDescription, String taskPriority, String taskDueDate) {
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
		
		return taskList.createDummyTask(taskName, taskDescription, formattedDueDate, formattedTaskPriority);
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
	
	public void createRecurrenceRule(Task task, String recurrencePattern, String recurrenceDay, LocalDate recurrenceStartDate, LocalDate recurrenceEndDate) {
		RecurrenceRule recurrenceRule = new RecurrenceRule(recurrencePattern, recurrenceDay, task, recurrenceStartDate, recurrenceEndDate);
		this.recurrenceRules.add(recurrenceRule);
		generateRecurrenceTasks(recurrenceRule);
	}
	
	public void generateRecurrenceTasks(RecurrenceRule recurrenceRule) {
		String pattern = recurrenceRule.getPattern();
		if (pattern.equals("")) {
			return;
		}
		
		Task originalTask = recurrenceRule.getTask();
		LocalDate startDate = recurrenceRule.getStartDate();
		LocalDate endDate = recurrenceRule.getEndDate();
		LocalDate currentDate = LocalDate.now();
		
		if (endDate.isBefore(currentDate)) {
			return;
		}
		
		LocalDate baseDate;
		if (startDate.isAfter(currentDate)) {
			baseDate = startDate;
		}
		else {
			baseDate = currentDate;
		}
		
		if (pattern.equals("Daily")) {
			LocalDate dueDate = baseDate;
			for (int i = 0; i < 3; i++) {
				if (dueDate.isAfter(endDate)) {
					break;
				}
				
				String newTitle = originalTask.getTitle() + " (" + dueDate.toString() + ")";
				if (getTaskByName(newTitle) == null) {
					taskList.createTask(newTitle, originalTask.getDescription(), dueDate, originalTask.getPriority());
				}
				
				dueDate = dueDate.plusDays(1);
			}
		}
		
		else if (pattern.equals("Weekly")) {
			LocalDate dueDate = baseDate;
			DayOfWeek targetDay = DayOfWeek.valueOf(recurrenceRule.getPatternKey().toUpperCase());
			
			while (!dueDate.getDayOfWeek().equals(targetDay)) {
				dueDate = dueDate.plusDays(1);
			}
			
			for (int i = 0; i < 3; i++) {
				if (dueDate.isAfter(endDate)) {
					break;
				}
				
				String newTitle = originalTask.getTitle() + " (" + dueDate.toString() + ")";
				if (getTaskByName(newTitle) == null) {
					taskList.createTask(newTitle, originalTask.getDescription(), dueDate, originalTask.getPriority());
				}
				
				dueDate = dueDate.plusWeeks(1);
			}
		}
		
		else if (pattern.equals("Monthly")) {
			int targetDay = Integer.parseInt(recurrenceRule.getPatternKey());
			LocalDate dueDate = baseDate.withDayOfMonth(1);
			
			if (baseDate.getDayOfMonth() > targetDay) {
				dueDate = dueDate.plusMonths(1);
			}
			
			for (int i = 0; i < 3; i++) {
				int lastDayOfMonth = dueDate.lengthOfMonth();
				if (targetDay <= lastDayOfMonth) {
					LocalDate candidateDate = dueDate.withDayOfMonth(targetDay);
					
					if (!candidateDate.isBefore(baseDate)) {
						if (candidateDate.isAfter(endDate)) {
							break;
						}
						
						String newTitle = originalTask.getTitle() + " (" + candidateDate.toString() + ")";
						if (getTaskByName(newTitle) == null) {
							taskList.createTask(newTitle, originalTask.getDescription(), dueDate, originalTask.getPriority());
						}
					}
				}
				
				dueDate = dueDate.plusMonths(1).withDayOfMonth(1);
			}
		}
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
			break;
		case "2":
			formattedCategory = Category.INTERMEDIATE;
			break;
		case "3":
			formattedCategory = Category.SENIOR;
			break;
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
	
	private String cleanCsv(String value) {
		if (value == null) return "";
		return value.replace(",", "<comma>").replace("\n", "<newline>");
	}

	private String uncleanCsv(String value) {
		if (value == null) return "";
		return value.replace("<comma>", ",").replace("<newline>", "\n");
	}

	private ArrayList<Tag> parseTags(String tagString) {
		ArrayList<Tag> output = new ArrayList<Tag>();
		if (tagString == null || tagString.equals("")) return output;
		
		String[] splitTags = tagString.split("\\|");
		for (int i = 0; i < splitTags.length; i++) {
			Tag tag = getTagByName(splitTags[i]);
			if (tag != null) {
				output.add(tag);
			}
		}
		return output;
	}

	private String tagsToString(ArrayList<Tag> tags) {
		String output = "";
		for (int i = 0; i < tags.size(); i++) {
			output += tags.get(i).getTitle();
			if (i < tags.size() - 1) {
				output += "|";
			}
		}
		return output;
	}

	private Priority parsePriority(String priority) {
		if (priority.equals("LOW")) return Priority.LOW;
		if (priority.equals("MEDIUM")) return Priority.MEDIUM;
		if (priority.equals("HIGH")) return Priority.HIGH;
		return null;
	}

	private TaskStatus parseStatus(String status) {
		if (status.equals("OPEN")) return TaskStatus.OPEN;
		if (status.equals("COMPLETED")) return TaskStatus.COMPLETED;
		if (status.equals("CANCELLED")) return TaskStatus.CANCELLED;
		return null;
	}

	private Category parseCategory(String category) {
		if (category.equals("JUNIOR")) return Category.JUNIOR;
		if (category.equals("INTERMEDIATE")) return Category.INTERMEDIATE;
		if (category.equals("SENIOR")) return Category.SENIOR;
		return null;
	}
	
	public void exportDatabase() {
		try {
			Files.createDirectories(Paths.get("data"));
			exportTags();
			exportProjects();
			exportCollaborators();
			exportTasks();
			exportRecurrenceRules();
			exportActivityEntries();
		}
		catch(Exception e) {
			System.out.println("Error exporting database");
		}
	}

	public void importDatabase() {
		try {
			this.taskList = new TaskList();
			this.projectList = new ProjectList();
			this.tagList = new TagList();
			this.activityEntryList = new ActivityEntryList();
			this.recurrenceRules = new ArrayList<RecurrenceRule>();
			
			importTags();
			importProjects();
			importCollaborators();
			importTasks();
			importRecurrenceRules();
			importActivityEntries();
		}
		catch(Exception e) {
			System.out.println("Error importing database");
		}
	}
	
	private void exportTags() {
		try {
			PrintWriter writer = new PrintWriter("data/tags.csv");
			writer.println("title");
			for (Tag tag : getTags()) {
				writer.println(cleanCsv(tag.getTitle()));
			}
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Could not export tags");
		}
	}

	private void importTags() {
		File file = new File("data/tags.csv");
		if (!file.exists()) return;
		
		try {
			Scanner fileScanner = new Scanner(file);
			if (fileScanner.hasNextLine()) fileScanner.nextLine();
			
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				if (!line.equals("")) {
					createTag(uncleanCsv(line));
				}
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.out.println("Could not import tags");
		}
	}
	
	private void exportProjects() {
		try {
			PrintWriter writer = new PrintWriter("data/projects.csv");
			writer.println("name,description");
			for (Project project : getProjects()) {
				writer.println(cleanCsv(project.getName()) + "," + cleanCsv(project.getDescription()));
			}
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Could not export projects");
		}
	}

	private void importProjects() {
		File file = new File("data/projects.csv");
		if (!file.exists()) return;
		
		try {
			Scanner fileScanner = new Scanner(file);
			if (fileScanner.hasNextLine()) fileScanner.nextLine();
			
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] parts = line.split(",", -1);
				if (parts.length >= 2) {
					createProject(uncleanCsv(parts[0]), uncleanCsv(parts[1]));
				}
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.out.println("Could not import projects");
		}
	}
	
	private void exportCollaborators() {
		try {
			PrintWriter writer = new PrintWriter("data/collaborators.csv");
			writer.println("project,name,category");
			for (Project project : getProjects()) {
				for (Collaborator collaborator : project.getCollaborators()) {
					writer.println(cleanCsv(project.getName()) + "," + cleanCsv(collaborator.getName()) + "," + collaborator.getCategory());
				}
			}
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Could not export collaborators");
		}
	}

	private void importCollaborators() {
		File file = new File("data/collaborators.csv");
		if (!file.exists()) return;
		
		try {
			Scanner fileScanner = new Scanner(file);
			if (fileScanner.hasNextLine()) fileScanner.nextLine();
			
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] parts = line.split(",", -1);
				if (parts.length >= 3) {
					Project project = getProjectByName(uncleanCsv(parts[0]));
					if (project != null) {
						Collaborator collaborator = new Collaborator(uncleanCsv(parts[1]), parseCategory(parts[2]));
						project.addCollaborator(collaborator);
					}
				}
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.out.println("Could not import collaborators");
		}
	}
	
	private void exportTasks() {
		try {
			PrintWriter writer = new PrintWriter("data/tasks.csv");
			writer.println("type,title,description,creation_date,due_date,priority,status,project,parent_task,collaborator,tags");
			
			for (Task task : taskList.getTasks()) {
				String type = "TASK";
				String parentTask = "";
				String collaborator = "";
				
				if (task instanceof CollaboratorTask) {
					type = "COLLABORATORTASK";
					parentTask = ((CollaboratorTask) task).getParentTask().getTitle();
					collaborator = ((CollaboratorTask) task).getCollaborator().getName();
				}
				else if (task instanceof Subtask) {
					type = "SUBTASK";
					parentTask = ((Subtask) task).getParentTask().getTitle();
				}
				
				String projectName = "";
				if (task.getProject() != null) {
					projectName = task.getProject().getName();
				}
				
				String dueDate = "";
				if (task.getDueDate() != null) {
					dueDate = task.getDueDate().toString();
				}
				
				writer.println(
					type + "," +
					cleanCsv(task.getTitle()) + "," +
					cleanCsv(task.getDescription()) + "," +
					task.getCreationDate() + "," +
					dueDate + "," +
					task.getPriority() + "," +
					task.getStatus() + "," +
					cleanCsv(projectName) + "," +
					cleanCsv(parentTask) + "," +
					cleanCsv(collaborator) + "," +
					cleanCsv(tagsToString(task.getTags()))
				);
			}
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Could not export tasks");
		}
	}
	
	private void importTasks() {
		File file = new File("data/tasks.csv");
		if (!file.exists()) return;
		
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			Scanner fileScanner = new Scanner(file);
			if (fileScanner.hasNextLine()) fileScanner.nextLine();
			
			while(fileScanner.hasNextLine()) {
				lines.add(fileScanner.nextLine());
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.out.println("Could not read tasks file");
			return;
		}
		
		for (String line : lines) {
			String[] parts = line.split(",", -1);
			if (parts.length < 11) continue;
			if (!parts[0].equals("TASK")) continue;
			
			String title = uncleanCsv(parts[1]);
			String description = uncleanCsv(parts[2]);
			LocalDate creationDate = LocalDate.parse(parts[3]);
			LocalDate dueDate = null;
			if (!parts[4].equals("")) dueDate = LocalDate.parse(parts[4]);
			Priority priority = parsePriority(parts[5]);
			TaskStatus status = parseStatus(parts[6]);
			Project project = null;
			if (!parts[7].equals("")) project = getProjectByName(uncleanCsv(parts[7]));
			ArrayList<Tag> tags = parseTags(uncleanCsv(parts[10]));
			
			Task task = new Task(title, description, dueDate, priority);
			task.setCreationDate(creationDate);
			task.setStatus(status);
			task.setProject(project);
			task.setTags(tags);
			taskList.addTask(task);
		}
		
		for (String line : lines) {
			String[] parts = line.split(",", -1);
			if (parts.length < 11) continue;
			if (!parts[0].equals("SUBTASK")) continue;
			
			String title = uncleanCsv(parts[1]);
			String description = uncleanCsv(parts[2]);
			LocalDate creationDate = LocalDate.parse(parts[3]);
			LocalDate dueDate = null;
			if (!parts[4].equals("")) dueDate = LocalDate.parse(parts[4]);
			Priority priority = parsePriority(parts[5]);
			TaskStatus status = parseStatus(parts[6]);
			Task parentTask = getTaskByName(uncleanCsv(parts[8]));
			ArrayList<Tag> tags = parseTags(uncleanCsv(parts[10]));
			
			if (parentTask != null) {
				taskList.createImportedSubtask(parentTask, title, description, creationDate, dueDate, priority, status, tags);
			}
		}
		
		for (String line : lines) {
			String[] parts = line.split(",", -1);
			if (parts.length < 11) continue;
			if (!parts[0].equals("COLLABORATORTASK")) continue;
			
			String title = uncleanCsv(parts[1]);
			String description = uncleanCsv(parts[2]);
			LocalDate creationDate = LocalDate.parse(parts[3]);
			LocalDate dueDate = null;
			if (!parts[4].equals("")) dueDate = LocalDate.parse(parts[4]);
			Priority priority = parsePriority(parts[5]);
			TaskStatus status = parseStatus(parts[6]);
			String projectName = uncleanCsv(parts[7]);
			Task parentTask = getTaskByName(uncleanCsv(parts[8]));
			String collaboratorName = uncleanCsv(parts[9]);
			ArrayList<Tag> tags = parseTags(uncleanCsv(parts[10]));
			
			Project project = getProjectByName(projectName);
			if (project != null && parentTask != null) {
				Collaborator collaborator = getCollaboratorByName(project, collaboratorName);
				if (collaborator != null) {
					taskList.createImportedCollaboratorTask(collaborator, parentTask, title, description, creationDate, dueDate, priority, status, tags);
				}
			}
		}
	}
	
	private void exportRecurrenceRules() {
		try {
			PrintWriter writer = new PrintWriter("data/recurrence_rules.csv");
			writer.println("task,pattern,pattern_key,start_date,end_date");
			
			for (RecurrenceRule rule : recurrenceRules) {
				writer.println(
					cleanCsv(rule.getTask().getTitle()) + "," +
					cleanCsv(rule.getPattern()) + "," +
					cleanCsv(rule.getPatternKey()) + "," +
					rule.getStartDate() + "," +
					rule.getEndDate()
				);
			}
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Could not export recurrence rules");
		}
	}

	private void importRecurrenceRules() {
		File file = new File("data/recurrence_rules.csv");
		if (!file.exists()) return;
		
		try {
			Scanner fileScanner = new Scanner(file);
			if (fileScanner.hasNextLine()) fileScanner.nextLine();
			
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] parts = line.split(",", -1);
				if (parts.length >= 5) {
					Task task = getTaskByName(uncleanCsv(parts[0]));
					if (task != null) {
						RecurrenceRule recurrenceRule = new RecurrenceRule(
							uncleanCsv(parts[1]),
							uncleanCsv(parts[2]),
							task,
							LocalDate.parse(parts[3]),
							LocalDate.parse(parts[4])
						);
						recurrenceRules.add(recurrenceRule);
					}
				}
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.out.println("Could not import recurrence rules");
		}
	}
	
	private void exportActivityEntries() {
		try {
			PrintWriter writer = new PrintWriter("data/activity_entries.csv");
			writer.println("task,timestamp,description");
			
			for (ActivityEntry entry : activityEntryList.getActivityEntryList()) {
				writer.println(
					cleanCsv(entry.getTask().getTitle()) + "," +
					entry.getTimestamp() + "," +
					cleanCsv(entry.getDescription())
				);
			}
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Could not export activity entries");
		}
	}

	private void importActivityEntries() {
		File file = new File("data/activity_entries.csv");
		if (!file.exists()) return;
		
		try {
			Scanner fileScanner = new Scanner(file);
			if (fileScanner.hasNextLine()) fileScanner.nextLine();
			
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] parts = line.split(",", -1);
				if (parts.length >= 3) {
					Task task = getTaskByName(uncleanCsv(parts[0]));
					if (task != null) {
						LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
						String description = uncleanCsv(parts[2]);
						activityEntryList.createActivityEntry(task, description, timestamp);
					}
				}
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.out.println("Could not import activity entries");
		}
	}
	
	public void exportSelectedTasks(ArrayList<Task> selectedTasks) {
		try {
			Files.createDirectories(Paths.get("data"));
			PrintWriter writer = new PrintWriter("data/exported_tasks.csv");
			writer.println("type,title,description,creation_date,due_date,priority,status,project,parent_task,collaborator,tags");
			
			for (Task task : selectedTasks) {
				String type = "TASK";
				String parentTask = "";
				String collaborator = "";
				
				if (task instanceof CollaboratorTask) {
					type = "COLLABORATORTASK";
					parentTask = ((CollaboratorTask) task).getParentTask().getTitle();
					collaborator = ((CollaboratorTask) task).getCollaborator().getName();
				}
				else if (task instanceof Subtask) {
					type = "SUBTASK";
					parentTask = ((Subtask) task).getParentTask().getTitle();
				}
				
				String projectName = "";
				if (task.getProject() != null) {
					projectName = task.getProject().getName();
				}
				
				String dueDate = "";
				if (task.getDueDate() != null) {
					dueDate = task.getDueDate().toString();
				}
				
				writer.println(
					type + "," +
					cleanCsv(task.getTitle()) + "," +
					cleanCsv(task.getDescription()) + "," +
					task.getCreationDate() + "," +
					dueDate + "," +
					task.getPriority() + "," +
					task.getStatus() + "," +
					cleanCsv(projectName) + "," +
					cleanCsv(parentTask) + "," +
					cleanCsv(collaborator) + "," +
					cleanCsv(tagsToString(task.getTags()))
				);
			}
			
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Could not export selected tasks");
		}
	}
	
	public void importTasksFromFile() {
		File file = new File("data/import.csv");
		if (!file.exists()) {
			System.out.println("Could not find data/import.csv");
			return;
		}
		
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			Scanner fileScanner = new Scanner(file);
			if (fileScanner.hasNextLine()) fileScanner.nextLine();
			
			while(fileScanner.hasNextLine()) {
				lines.add(fileScanner.nextLine());
			}
			fileScanner.close();
		}
		catch(Exception e) {
			System.out.println("Could not read import file");
			return;
		}
		
		for (String line : lines) {
			String[] parts = line.split(",", -1);
			if (parts.length < 11) continue;
			if (!parts[0].equals("TASK")) continue;
			
			String title = uncleanCsv(parts[1]);
			if (getTaskByName(title) != null) {
				System.out.println("Skipped task '" + title + "': task already exists");
				continue;
			}
			
			String description = uncleanCsv(parts[2]);
			LocalDate creationDate = LocalDate.parse(parts[3]);
			LocalDate dueDate = null;
			if (!parts[4].equals("")) dueDate = LocalDate.parse(parts[4]);
			Priority priority = parsePriority(parts[5]);
			TaskStatus status = parseStatus(parts[6]);
			String projectName = uncleanCsv(parts[7]);
			ArrayList<Tag> tags = parseTagsCreatingMissing(uncleanCsv(parts[10]));
			
			Project project = null;
			if (!projectName.equals("")) {
				project = getProjectByName(projectName);
				if (project == null) {
					createProject(projectName, "");
					project = getProjectByName(projectName);
				}
			}
			
			Task task = new Task(title, description, dueDate, priority);
			task.setCreationDate(creationDate);
			task.setStatus(status);
			task.setProject(project);
			task.setTags(tags);
			taskList.addTask(task);
		}
		
		for (String line : lines) {
			String[] parts = line.split(",", -1);
			if (parts.length < 11) continue;
			if (!parts[0].equals("SUBTASK")) continue;
			
			String title = uncleanCsv(parts[1]);
			if (getTaskByName(title) != null) {
				System.out.println("Skipped task '" + title + "': task already exists");
				continue;
			}
			
			String description = uncleanCsv(parts[2]);
			LocalDate creationDate = LocalDate.parse(parts[3]);
			LocalDate dueDate = null;
			if (!parts[4].equals("")) dueDate = LocalDate.parse(parts[4]);
			Priority priority = parsePriority(parts[5]);
			TaskStatus status = parseStatus(parts[6]);
			String parentTaskName = uncleanCsv(parts[8]);
			ArrayList<Tag> tags = parseTagsCreatingMissing(uncleanCsv(parts[10]));
			
			Task parentTask = getTaskByName(parentTaskName);
			if (parentTask != null) {
				taskList.createImportedSubtask(parentTask, title, description, creationDate, dueDate, priority, status, tags);
			}
		}
		
		for (String line : lines) {
			String[] parts = line.split(",", -1);
			if (parts.length < 11) continue;
			if (!parts[0].equals("COLLABORATORTASK")) continue;
			
			String title = uncleanCsv(parts[1]);
			if (getTaskByName(title) != null) {
				System.out.println("Skipped task '" + title + "': task already exists");
				continue;
			}
			
			String description = uncleanCsv(parts[2]);
			LocalDate creationDate = LocalDate.parse(parts[3]);
			LocalDate dueDate = null;
			if (!parts[4].equals("")) dueDate = LocalDate.parse(parts[4]);
			Priority priority = parsePriority(parts[5]);
			TaskStatus status = parseStatus(parts[6]);
			String projectName = uncleanCsv(parts[7]);
			String parentTaskName = uncleanCsv(parts[8]);
			String collaboratorName = uncleanCsv(parts[9]);
			ArrayList<Tag> tags = parseTagsCreatingMissing(uncleanCsv(parts[10]));
			
			Project project = null;
			if (!projectName.equals("")) {
				project = getProjectByName(projectName);
				if (project == null) {
					createProject(projectName, "");
					project = getProjectByName(projectName);
				}
			}
			
			Task parentTask = getTaskByName(parentTaskName);
			if (project != null && parentTask != null) {
				Collaborator collaborator = getCollaboratorByName(project, collaboratorName);
				if (collaborator == null) {
					project.addCollaborator(new Collaborator(collaboratorName, Category.JUNIOR));
					collaborator = getCollaboratorByName(project, collaboratorName);
				}
				
				if (collaborator != null) {
					taskList.createImportedCollaboratorTask(collaborator, parentTask, title, description, creationDate, dueDate, priority, status, tags);
				}
			}
		}
	}
	
	private ArrayList<Tag> parseTagsCreatingMissing(String tagString) {
		ArrayList<Tag> output = new ArrayList<Tag>();
		if (tagString == null || tagString.equals("")) return output;
		
		String[] splitTags = tagString.split("\\|");
		for (int i = 0; i < splitTags.length; i++) {
			String tagTitle = splitTags[i];
			Tag tag = getTagByName(tagTitle);
			if (tag == null) {
				createTag(tagTitle);
				tag = getTagByName(tagTitle);
			}
			if (tag != null) {
				output.add(tag);
			}
		}
		return output;
	}
	
	public void exportSelectedTasksToIcs(ArrayList<Task> selectedTasks) {
		try {
			Files.createDirectories(Paths.get("data"));
			PrintWriter writer = new PrintWriter("data/exported_tasks.ics");
			
			writer.println("BEGIN:VCALENDAR");
			writer.println("VERSION:2.0");
			writer.println("PRODID:-//TaskSystem//EN");
			
			for (Task task : selectedTasks) {
				if (task instanceof Subtask) {
					continue;
				}
				
				if (task.getDueDate() == null) {
					continue;
				}
				
				String description = "";
				
				description += "Description: " + cleanIcs(task.getDescription());
				description += "\\nStatus: " + task.getStatus();
				description += "\\nPriority: " + task.getPriority();
				description += "\\nDue date: " + task.getDueDate();
				
				if (task.getProject() != null) {
					description += "\\nProject: " + cleanIcs(task.getProject().getName());
				}
				
				ArrayList<Subtask> subtasks = getTaskSubtasks(task);
				if (!subtasks.isEmpty()) {
					description += "\\nSubtasks:";
					for (int i = 0; i < subtasks.size(); i++) {
						description += "\\n- " + cleanIcs(subtasks.get(i).getTitle()) + " (" + subtasks.get(i).getStatus() + ")";
					}
				}
				
				String dueDate = task.getDueDate().toString().replace("-", "");
				
				writer.println("BEGIN:VEVENT");
				writer.println("UID:" + System.currentTimeMillis() + "_" + cleanIcs(task.getTitle()).replace(" ", "_") + "@tasksystem");
				writer.println("DTSTAMP:" + LocalDateTime.now().toString().replace("-", "").replace(":", "").replace(".", "").substring(0, 15));
				writer.println("DTSTART;VALUE=DATE:" + dueDate);
				writer.println("DTEND;VALUE=DATE:" + task.getDueDate().plusDays(1).toString().replace("-", ""));
				writer.println("SUMMARY:" + cleanIcs(task.getTitle()));
				writer.println("DESCRIPTION:" + description);
				writer.println("END:VEVENT");
			}
			
			writer.println("END:VCALENDAR");
			writer.close();
		}
		catch(Exception e) {
			System.out.println("Could not export tasks to .ics");
		}
	}
	
	private String cleanIcs(String value) {
		if (value == null) return "";
		
		String output = value;
		output = output.replace("\\", "\\\\");
		output = output.replace(",", "\\,");
		output = output.replace(";", "\\;");
		output = output.replace("\n", "\\n");
		output = output.replace("\r", "");
		
		return output;
	}
	 
}
