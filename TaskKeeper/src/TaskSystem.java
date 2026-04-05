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
		if (getTaskByName(taskName) != null) {
			System.out.println("Could not create task '" + taskName + "': Task name already in use");
			return;
		}
		
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
		if (getTaskByName(taskName) != null) {
			System.out.println("Could not create task '" + taskName + "': Task name already in use");
			return;
		}
		
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
	
	public ArrayList<Task> getSubtasks(Task task){
		ArrayList<Task> output = new ArrayList<Task>();
		for (Task targetTask : taskList.getTasks()) {
			if (targetTask.getParentTask() == null) continue;
			if (targetTask.getParentTask().getTitle().equalsIgnoreCase(task.getTitle())) output.add(targetTask);
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
	 public void exportToCSV(String fileName) throws IOException {
	        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
	            writer.write("title,description,creationDate,dueDate,priority,status");
	            writer.newLine();

	            for (Task task : taskList.getTasks()) {
	                writer.write(task.getTitle());
	                writer.write(",");
	                writer.write(task.getDescription());
	                writer.write(",");
	                writer.write(task.getCreationDate().toString());
	                writer.write(",");
	                writer.write(task.getDueDate() == null ? "" : task.getDueDate().toString());
	                writer.write(",");
	                writer.write(task.getPriority().toString());
	                writer.write(",");
	                writer.write(task.getStatus().toString());
	                writer.write(",");
	                writer.write(task.getParentTask().getTitle());
	                writer.write(",");
	                String tagNames = task.getTags().stream().map(Tag::getTitle).collect(Collectors.joining("|"));
	                writer.write(tagNames);
	                writer.newLine();
	            }
	        }
	        
	        
	    }
	 	
	 public void importFromCSV(String fileName) throws IOException {
	        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
	            String line;
	            boolean firstLine = true;

	            while ((line = reader.readLine()) != null) {
	                if (firstLine) {
	                    firstLine = false;
	                    continue; // skip header
	                }

	                List<String> fields = parseCSVLine(line);
	                if (fields.size() < 6) {
	                    continue;
	                }

	                String title = fields.get(0);
	                String description = fields.get(1);
	                String creationDate = fields.get(2);
	                String dueDate = fields.get(3);
	                String priority = fields.get(4);
	                String status = fields.get(5);
	                String parentTask = fields.get(6);
	                String tagsField = fields.get(7);

	                ArrayList<Tag> tags = new ArrayList<>();

	                if (!tagsField.isEmpty()) {
	                    String[] tagNames = tagsField.split("\\|");
	                    for (String tagName : tagNames) {
	                        tags.add(new Tag(tagName));
	                    }
	                }

	                createImportedTask(title, description, creationDate, dueDate, priority, status, parentTask, tags);
	            }
	        }
	        
	        
	    }
	 
	 private List<String> parseCSVLine(String line) {
	        ArrayList<String> fields = new ArrayList<>();
	        StringBuilder current = new StringBuilder();
	        boolean inQuotes = false;

	        for (int i = 0; i < line.length(); i++) {
	            char c = line.charAt(i);

	            if (c == '"') {
	                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
	                    current.append('"');
	                    i++;
	                } else {
	                    inQuotes = !inQuotes;
	                }
	            } else if (c == ',' && !inQuotes) {
	                fields.add(current.toString());
	                current.setLength(0);
	            } else {
	                current.append(c);
	            }
	        }

	        fields.add(current.toString());
	        return fields;
	    }
	
	
}
