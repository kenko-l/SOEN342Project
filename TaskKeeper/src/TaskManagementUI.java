import java.time.*;
import java.util.*;


public class TaskManagementUI {
	static TaskSystem taskSystem = new TaskSystem();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		String UserInput;
		while(true) {
			displayMain();
			while(true) {
				System.out.print("Select an option:");
				UserInput = sc.nextLine().trim();
				if (UserInput.equals("1") || UserInput.equals("2") || UserInput.equals("3")) break;
				System.out.println("Invalid selection");
			}
			
			switch(UserInput) {
			case "1":
				handleTaskManagement();
				break;
			case "2":
				handleTaskView();
				break;
			case "3":
				//handleImportExport();
			}
				 
		}
	}
	
	private static void displayMain() {
		System.out.println("1. Task Management");
		System.out.println("2. Task View");
		System.out.println("3. Import/Export");
	}
	
	//---------------------------------------------------------------------------
	//Task Management
	//---------------------------------------------------------------------------

	private static void handleTaskManagement() {
		while(true) {
			System.out.println("1. Create a new task");
			System.out.println("2. Update a task");
			System.out.println("3. Create a project");
			System.out.println("4. Update a project");
			System.out.println("5. Create a tag");
			System.out.println("6. View task activity");
			System.out.println("7. Go back");
			String UserInput;
			while(true) {
				System.out.print("Select an option:");
				UserInput = sc.nextLine().trim();
				if (UserInput.equals("1") || UserInput.equals("2") || UserInput.equals("3") || UserInput.equals("4") || UserInput.equals("5") || UserInput.equals("6") || UserInput.equals("7")) break;
				System.out.println("Invalid selection");
			}
			switch(UserInput) {
			case "1":
				handleTaskCreation("Task", null, null);
				break;
			case "2":
				handleTaskUpdate();
				break;
			case "3":
				handleProjectCreation();
				break;
			case "4":
				handleProjectUpdate();
				break;
			case "5":
				handleTagCreation();
				break;
			case "6":
				handleActivityView();
				break;
			case "7":
				return;
			}
		}
	}
	
	private static void handleTaskCreation(String taskType, Task parentTask, Collaborator collaborator) {
		String taskName;
		String taskDescription;
		String taskPriority;
		String taskDueDate;
		String recurringOption = "2";
		String recurrencePattern = "";
		String recurrenceDay = "";
		LocalDate recurrenceStartDate = null;
		LocalDate recurrenceEndDate = null;
		
		while(true) {
			System.out.print("Enter task name: ");
			taskName = sc.nextLine().trim();
			if (!taskName.equals("") && taskSystem.getTaskByName(taskName) == null) break;
			else System.out.println("Task name cannot already exist or be blank");
		}
		System.out.print("Enter task description (Press enter to leave blank): ");
		taskDescription = sc.nextLine();
		while(true) {
			System.out.println("Choose priority level:");
			System.out.println("1. Low");
			System.out.println("2. Medium");
			System.out.println("3. High");
			taskPriority = sc.nextLine().trim();
			if (taskPriority.equals("1") || taskPriority.equals("2") || taskPriority.equals("3")) break;
			else System.out.println("Invalid selection");
		}
		while(true) {
			System.out.println("Enter task due date YYYY-MM-DD (Press enter to leave blank): ");
			taskDueDate = sc.nextLine();
			if (taskDueDate.equals("")) break;
			try {
				LocalDate.parse(taskDueDate);
				break;
			}
			catch(Exception e) {
				System.out.println("Invalid date format");
			}
		}
		
		if (taskType.equals("Task")) {
			while(true) {
				System.out.println("Is this a recurring task?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				recurringOption = sc.nextLine().trim();
				if (recurringOption.equals("1") || recurringOption.equals("2")) break;
				else System.out.println("Invalid selection");
			}
			
			if (recurringOption.equals("1")) {
				while(true) {
					System.out.println("Choose recurrence pattern:");
					System.out.println("1. Daily");
					System.out.println("2. Weekly");
					System.out.println("3. Monthly");
					String recurrenceInput = sc.nextLine().trim();
					if (recurrenceInput.equals("1")) {
						recurrencePattern = "Daily";
						break;
					}
					else if (recurrenceInput.equals("2")) {
						recurrencePattern = "Weekly";
						break;
					}
					else if (recurrenceInput.equals("3")) {
						recurrencePattern = "Monthly";
						break;
					}
					else System.out.println("Invalid selection");
				}
				
				if (recurrencePattern.equals("Weekly")) {
					while(true) {
						System.out.println("Choose day of week:");
						System.out.println("1. Monday");
						System.out.println("2. Tuesday");
						System.out.println("3. Wednesday");
						System.out.println("4. Thursday");
						System.out.println("5. Friday");
						System.out.println("6. Saturday");
						System.out.println("7. Sunday");
						String dayInput = sc.nextLine().trim();
						if (dayInput.equals("1")) {
							recurrenceDay = "MONDAY";
							break;
						}
						else if (dayInput.equals("2")) {
							recurrenceDay = "TUESDAY";
							break;
						}
						else if (dayInput.equals("3")) {
							recurrenceDay = "WEDNESDAY";
							break;
						}
						else if (dayInput.equals("4")) {
							recurrenceDay = "THURSDAY";
							break;
						}
						else if (dayInput.equals("5")) {
							recurrenceDay = "FRIDAY";
							break;
						}
						else if (dayInput.equals("6")) {
							recurrenceDay = "SATURDAY";
							break;
						}
						else if (dayInput.equals("7")) {
							recurrenceDay = "SUNDAY";
							break;
						}
						else System.out.println("Invalid selection");
					}
				}
				else if (recurrencePattern.equals("Monthly")) {
					while(true) {
						System.out.println("Enter day of month: ");
						String dayInput = sc.nextLine().trim();
						try {
							int dayOfMonth = Integer.parseInt(dayInput);
							if (dayOfMonth >= 1 && dayOfMonth <= 31) {
								recurrenceDay = dayInput;
								break;
							}
							else System.out.println("Day must be between 1 and 31");
						}
						catch(Exception e) {
							System.out.println("Invalid day");
						}
					}
				}
				
				while(true) {
					try {
						System.out.println("Enter recurrence start date YYYY-MM-DD: ");
						recurrenceStartDate = LocalDate.parse(sc.nextLine().trim());
						System.out.println("Enter recurrence end date YYYY-MM-DD: ");
						recurrenceEndDate = LocalDate.parse(sc.nextLine().trim());
						if (recurrenceEndDate.isBefore(recurrenceStartDate)) {
							System.out.println("End date cannot be before start date");
						}
						else {
							break;
						}
					}
					catch(Exception e) {
						System.out.println("Invalid date format");
					}
				}
				Task task = taskSystem.createDummyTask(taskName, taskDescription, taskPriority, taskDueDate);
				taskSystem.createRecurrenceRule(task, recurrencePattern, recurrenceDay, recurrenceStartDate, recurrenceEndDate);
				return;
			}
		}
		
		if (taskType.equals("Task")) {
			taskSystem.createTask(taskName, taskDescription, taskPriority, taskDueDate);
			Task createdTask = taskSystem.getTaskByName(taskName);
			taskSystem.createActivityEntry(createdTask, "Task created");
			System.out.println("Task created successfully!");
		}
		else if (taskType.equals("Subtask")) {
			taskSystem.createSubtask(parentTask, taskName, taskDescription, taskPriority, taskDueDate);
			Task createdTask = taskSystem.getTaskByName(taskName);
			taskSystem.createActivityEntry(createdTask, "Subtask created");
			System.out.println("Subtask created successfully!");
		}
		else if (taskType.equals("CollaboratorTask")) {
			taskSystem.createCollaboratorTask(collaborator, parentTask, taskName, taskDescription, taskPriority, taskDueDate);
			Task createdTask = taskSystem.getTaskByName(taskName);
			taskSystem.createActivityEntry(createdTask, "Collaborator task created");
			System.out.println("Collaborator task created successfully!");
		}
	}
	
	private static void handleTaskUpdate() {
		System.out.println("Enter task name: ");
		String taskName = sc.nextLine();
		Task targetTask = taskSystem.getTaskByName(taskName);
		if (targetTask == null) {
			System.out.println("Can't find task with that name");
			return;
		}
		while(true) {
			ArrayList<Subtask> subtasks = taskSystem.getTaskSubtasks(targetTask);
			System.out.println(targetTask);
			if (!subtasks.isEmpty()) {
				System.out.println("\nSubtasks: ");
				for (int i = 0; i<subtasks.size(); i++) {
				System.out.println((i+1) + ": " + subtasks.get(i).getTitle() + " - " + subtasks.get(i).getStatus());
				}
			}
			System.out.println();
			System.out.println("1. Change title");
			System.out.println("2. Change description");
			System.out.println("3. Change priority");
			System.out.println("4. Change due date");
			System.out.println("5. Change status");
			System.out.println("6. Change project");
			System.out.println("7. Change tags");
			System.out.println("8. Add subtask");
			System.out.println("9. Assign Collaborator");
			System.out.println("10. Go back");
			
			String UserInput;
			while(true) {
				System.out.print("Select an option:");
				UserInput = sc.nextLine().trim();
				if (UserInput.equals("1") || UserInput.equals("2") || UserInput.equals("3") || UserInput.equals("4") || 
					UserInput.equals("5") || UserInput.equals("6") || UserInput.equals("7") || UserInput.equals("8") ||
					UserInput.equals("9") || UserInput.equals("10")) break;
				System.out.println("Invalid selection");
			}
			
			taskUpdateSwitch:
			switch(UserInput) {
			case "1":
				String newTitle;
				while(true) {
					System.out.print("Enter new title: ");
					newTitle = sc.nextLine().trim();
					if (!newTitle.equals("") && (taskSystem.getTaskByName(newTitle) == null || newTitle.equals(targetTask.getTitle()))) break;
					else System.out.println("Task name cannot already exist or be blank");
				}
				targetTask.setTitle(newTitle);
				taskSystem.createActivityEntry(targetTask, "Title updated: " + newTitle);
				System.out.println("Title updated successfully!");
				break;
				
			case "2":
				System.out.print("Enter new description (Press enter to leave blank): ");
				String newDescription = sc.nextLine();
				targetTask.setDescription(newDescription);
				taskSystem.createActivityEntry(targetTask, "Description updated: " + newDescription);
				System.out.println("Description updated successfully!");
				break;
				
			case "3":
				String newPriority;
				while(true) {
					System.out.println("1. Low");
					System.out.println("2. Medium");
					System.out.println("3. High");
					System.out.println("Choose priority level:");
					newPriority = sc.nextLine().trim();
					if (newPriority.equals("1") || newPriority.equals("2") || newPriority.equals("3")) break;
					else System.out.println("Invalid selection");
				}
				targetTask.setPriority(newPriority);
				taskSystem.createActivityEntry(targetTask, "Priority updated");
				System.out.println("Priority updated successfully!");
				break;
				
			case "4":
				String newDueDate;
				LocalDate formattedDueDate = null;
				while(true) {
					System.out.println("Enter new due date YYYY-MM-DD (Press enter to leave blank): ");
					newDueDate = sc.nextLine();
					if (newDueDate.equals("")) break;
					try {
						formattedDueDate = LocalDate.parse(newDueDate);
						break;
					}
					catch(Exception e) {
						System.out.println("Invalid date format");
					}
				}
				targetTask.setDueDate(formattedDueDate);
				taskSystem.createActivityEntry(targetTask, "Due date updated: " + formattedDueDate.toString());
				System.out.println("Due date updated successfully!");
				break;
				
			case "5":
				String newStatus;
				while(true) {
					System.out.println("1. Open");
					System.out.println("2. Completed");
					System.out.println("3. Cancelled");
					System.out.println("Choose status:");
					newStatus = sc.nextLine().trim();
					if (newStatus.equals("1") || newStatus.equals("2") || newStatus.equals("3")) break;
					else System.out.println("Invalid selection");
				}
				targetTask.setStatus(newStatus);
				if (newStatus.equals("2")) taskSystem.createActivityEntry(targetTask, "Task completed");
				else if (newStatus.equals("3")) taskSystem.createActivityEntry(targetTask, "Task cancelled");
				else taskSystem.createActivityEntry(targetTask, "Task opened");
				System.out.println("Status updated successfully!");
				break;
				
			case "6":
				if (targetTask instanceof Subtask) {
					System.out.println("Cannot change the project of a subtask");
					break;
				}
				ArrayList<CollaboratorTask> collaboratorTasks = taskSystem.getTaskCollaboratorTasks(targetTask);
				
				for (CollaboratorTask collaboratorTask : collaboratorTasks) {
					if (collaboratorTask.getStatus() == TaskStatus.OPEN) {
						System.out.println("Cannot change project if task has an open collaborated subtask");
						break taskUpdateSwitch;
					}
				}
				
				ArrayList<Project> projects = taskSystem.getProjects();
				String projectName;
				if (targetTask.getProject() == null) System.out.println("Current project: none");
				else System.out.println("Current project: " + targetTask.getProject().getName());
				while(true) {
					for (int i = 0; i<projects.size(); i++) {
						System.out.println((i+1) + ": " + projects.get(i).getName());
					}
					System.out.println("Enter project name (Press enter to unassign): ");
					projectName = sc.nextLine().trim();
					if (projectName.equals("")) break;
					else if (taskSystem.getProjectByName(projectName) != null) break;
					else System.out.println("Project does not exist");
				}
				taskSystem.updateTaskProject(targetTask, projectName);
				for (Task subtask : subtasks) {
					taskSystem.updateTaskProject(subtask, projectName);
					taskSystem.createActivityEntry(subtask, "Project updated: " + projectName);
				}
				taskSystem.createActivityEntry(targetTask, "Project updated: " + projectName);
				System.out.println("Project updated successfully!");
				break;
				
			case "7":
				String tagOption;
				while(true) {
					System.out.println("1. Remove tag");
					System.out.println("2. Add tag");
					System.out.println("Choose an option: ");
					tagOption = sc.nextLine().trim();
					if (tagOption.equals("1") || tagOption.equals("2")) break;
					else System.out.println("Invalid selection");
				}
				
				if (tagOption.equals("1")) {
					ArrayList<Tag> taskTags = targetTask.getTags();
					if (taskTags.size() == 0) {
						System.out.println("Task has no tags");
						break;
					}
					
					System.out.println("Current tags:");
					for (int i = 0; i < taskTags.size(); i++) {
						System.out.println((i + 1) + ". " + taskTags.get(i).getTitle());
					}
					
					String tagName;
					while(true) {
						System.out.println("Enter tag name to remove (Press enter to remove none): ");
						tagName = sc.nextLine().trim();
						if (tagName.equals("")) break;
						
						boolean exists = false;
						for (int i = 0; i < taskTags.size(); i++) {
							if (taskTags.get(i).getTitle().equalsIgnoreCase(tagName)) {
								exists = true;
								break;
							}
						}
						
						if (exists) break;
						else System.out.println("Task does not have this tag");
					}
					
					if (!tagName.equals("")) {
						taskSystem.removeTagFromTask(targetTask, tagName);
						taskSystem.createActivityEntry(targetTask, "Tag removed: " + tagName);
						System.out.println("Tag removed successfully!");
					}
				}
				else {
					ArrayList<Tag> allTags = taskSystem.getTags();
					if (allTags.size() == 0) {
						System.out.println("There are no tags to add");
						break;
					}
					
					System.out.println("All tags:");
					for (int i = 0; i < allTags.size(); i++) {
						System.out.println((i + 1) + ". " + allTags.get(i).getTitle());
					}
					
					String tagName;
					while(true) {
						System.out.println("Enter tag name to add (Press enter to add none): ");
						tagName = sc.nextLine().trim();
						if (tagName.equals("")) break;
						
						Tag tag = taskSystem.getTagByName(tagName);
						if (tag == null) {
							System.out.println("Tag does not exist");
							continue;
						}
						
						boolean alreadyExists = false;
						ArrayList<Tag> taskTags = targetTask.getTags();
						for (int i = 0; i < taskTags.size(); i++) {
							if (taskTags.get(i).getTitle().equalsIgnoreCase(tagName)) {
								alreadyExists = true;
								break;
							}
						}
						
						if (alreadyExists) System.out.println("Task already has this tag");
						else break;
					}
					
					if (!tagName.equals("")) {
						taskSystem.addTagToTask(targetTask, tagName);
						taskSystem.createActivityEntry(targetTask, ("Tag added: " + tagName));
						System.out.println("Tag added successfully!");
					}
				}
				break;				
			case "8":
				if (targetTask instanceof Subtask) {
					System.out.println("Cannot create a subtask for a subtask");
					break;
				}
				handleTaskCreation("Subtask", targetTask, null);
				break;
			case "9":
				if (targetTask instanceof Subtask) {
					System.out.println("Cannot assign a collaborator for a subtask");
					break;
				}
				if (targetTask.getProject() == null) {
					System.out.println("Task must belong to a project to add a collaborator");
					break;
				}
				ArrayList<Collaborator> collaborators = targetTask.getProject().getCollaborators();
				if (collaborators.isEmpty()) {
					System.out.println("Project has no listed collaborators");
				}
				for (Collaborator collaborator : collaborators) {
					System.out.println(collaborator);
					System.out.println();
				}
				String collaboratorName;
				Collaborator collaborator;
				while(true) {
					System.out.print("Enter collaborator name: ");
					collaboratorName = sc.nextLine().trim();
					collaborator = taskSystem.getCollaboratorByName(targetTask.getProject(), collaboratorName);
					if (collaborator != null) break;
					else System.out.println("Collaborator does not exist");
				}
				if (taskSystem.isAtCapacity(targetTask.getProject(), collaborator)) {
					System.out.println("Collaborator is at capacity");
					break;
				}
				handleTaskCreation("CollaboratorTask", targetTask, collaborator);
				break;
			case "10":
				return;
			}
		}
	}
	
	private static void handleProjectCreation() {
		String projectName;
		String projectDescription;
		
		while(true) {
			System.out.print("Enter project name: ");
			projectName = sc.nextLine().trim();
			if (projectName.equals("")) System.out.println("Project name cannot be empty");
			else if (taskSystem.getProjectByName(projectName) == null) break;
			else System.out.println("Project name already exists!");
		}
		
		System.out.print("Enter project description (Press enter to leave blank): ");
		projectDescription = sc.nextLine();
		
		taskSystem.createProject(projectName, projectDescription);
		System.out.println("Project created successfully!");
	}

	private static void handleProjectUpdate() {
		System.out.println("Enter project name: ");
		String projectName = sc.nextLine();
		Project targetProject = taskSystem.getProjectByName(projectName);
		if (targetProject == null) {
			System.out.println("Can't find project with that name");
			return;
		}
		
		while(true) {
			System.out.println(targetProject);
			System.out.println("1. Change title");
			System.out.println("2. Change description");
			System.out.println("3. Add collaborator");
			System.out.println("4. Go back");
			
			String UserInput;
			while(true) {
				System.out.print("Select an option:");
				UserInput = sc.nextLine().trim();
				if (UserInput.equals("1") || UserInput.equals("2") || UserInput.equals("3") || UserInput.equals("4")) break;
				System.out.println("Invalid selection");
			}
			
			switch(UserInput) {
			case "1":
				String newTitle;
				while(true) {
					System.out.print("Enter new title: ");
					newTitle = sc.nextLine().trim();
					if (newTitle.equals("")) {
						System.out.println("Title cannot be empty");
					}
					else if (taskSystem.getProjectByName(newTitle) == null || newTitle.equalsIgnoreCase(targetProject.getName())) {
						break;
					}
					else {
						System.out.println("Project name already exists!");
					}
				}
				targetProject.setName(newTitle);
				System.out.println("Title updated successfully!");
				break;
				
			case "2":
				System.out.print("Enter new description (Press enter to leave blank): ");
				targetProject.setDescription(sc.nextLine());
				System.out.println("Description updated successfully!");
				break;
				
			case "3":
				String collaboratorName;
				String collaboratorCategory;
				
				while(true) {
					System.out.print("Enter collaborator name: ");
					collaboratorName = sc.nextLine().trim();
					if (collaboratorName.equals("")) {
						System.out.println("Collaborator name cannot be empty");
					}
					else if (taskSystem.getCollaboratorByName(targetProject, collaboratorName) == null) {
						break;
					}
					else {
						System.out.println("Collaborator name already exists!");
					}
				}
				
				while(true) {
					System.out.println("1. Junior");
					System.out.println("2. Intermediate");
					System.out.println("3. Senior");
					System.out.println("Choose category:");
					collaboratorCategory = sc.nextLine().trim();
					if (collaboratorCategory.equals("1") || collaboratorCategory.equals("2") || collaboratorCategory.equals("3")) break;
					else System.out.println("Invalid selection");
				}
				
				taskSystem.createCollaborator(collaboratorName, collaboratorCategory, targetProject);
				System.out.println("Collaborator added successfully!");
				break;
			case "4":
				return;
			}
		}
	}

	private static void handleTagCreation() {
		String tagName;
		
		while(true) {
			System.out.print("Enter tag name: ");
			tagName = sc.nextLine().trim();
			if (tagName.equals("")) return;
			else if (taskSystem.getTagByName(tagName) == null) break;
			else System.out.println("Tag name already exists!");
		}
		
		taskSystem.createTag(tagName);
		System.out.println("Tag created successfully!");
	}
	
	private static void handleActivityView() {
		System.out.println("Enter task name: ");
		String taskName = sc.nextLine();
		Task targetTask = taskSystem.getTaskByName(taskName);
		if (taskName == null) {
			System.out.println("Can't find task with that name");
			return;
		}
		ArrayList<ActivityEntry> taskActivity = taskSystem.getActivity(targetTask);
		for (ActivityEntry activity : taskActivity) {
			System.out.println(activity);
		}
	}
	
	//------------------------------------------------------------
	//Task View
	//------------------------------------------------------------
	
	private static void handleTaskView() {
		String filterType = "None";
		String sortType = "Due date";
		String sortOrder = "Ascending";
		String filterPackage = "";
		
		while(true) {
			System.out.println("Filter: " + filterType +", " + filterPackage);
			System.out.println("Sorting: " + sortType + ", " + sortOrder);
			System.out.println("1. View tasks");
			System.out.println("2. Apply filter");
			System.out.println("3. Apply sorting");
			System.out.println("4. Go back");
			
			String UserInput;
			while(true) {
				System.out.print("Select an option:");
				UserInput = sc.nextLine().trim();
				if (UserInput.equals("1") || UserInput.equals("2") || UserInput.equals("3") || UserInput.equals("4")) break;
				System.out.println("Invalid selection");
			}
			
			switch(UserInput) {
			case "1":
				ArrayList<Task> selectedTasks = taskSystem.getTasks(filterType, filterPackage, sortType, sortOrder);
				for (Task task : selectedTasks) {
					ArrayList<Subtask> subtasks = taskSystem.getTaskSubtasks(task);
					System.out.println("/-----------------------------------------------/");
					System.out.println(task);
					if (!subtasks.isEmpty()) {
						System.out.println("\nSubtasks: ");
						for (int i = 0; i<subtasks.size(); i++) {
						System.out.println((i+1) + ": " + subtasks.get(i).getTitle() + " - " + subtasks.get(i).getStatus());
						}
					}
					System.out.println("/-----------------------------------------------/");
				}
				break;
				
			case "2":
				String filterInput;
				while(true) {
					System.out.println("Available filter types:");
					System.out.println("1. Keyword");
					System.out.println("2. Priority");
					System.out.println("3. Status");
					System.out.println("4. Project");
					System.out.println("5. Date range");
					System.out.println("6. Tag");
					System.out.println("7. None");
					System.out.print("Select an option:");
					filterInput = sc.nextLine().trim();
					if (filterInput.equals("1") || filterInput.equals("2") || filterInput.equals("3") || filterInput.equals("4") || filterInput.equals("5") || filterInput.equals("6") || filterInput.equals("7")) break;
					System.out.println("Invalid selection");
				}
				
				switch(filterInput) {
				case "1":
					System.out.println("Enter keyword: ");
					filterPackage = sc.nextLine().trim();
					filterType = "Keyword";
					break;
					
				case "2":
					String priorityInput;
					filterType = "Priority";
					while(true) {
						System.out.println("1. Low");
						System.out.println("2. Medium");
						System.out.println("3. High");
						System.out.println("Choose priority level:");
						priorityInput = sc.nextLine().trim();
						if (priorityInput.equals("1")) {
							filterPackage = "LOW";
							break;
						}
						else if (priorityInput.equals("2")) {
							filterPackage = "MEDIUM";
							break;
						}
						else if (priorityInput.equals("3")) {
							filterPackage = "HIGH";
							break;
						}
						else System.out.println("Invalid selection");
					}
					break;
					
				case "3":
					String statusInput;
					filterType = "Status";
					while(true) {
						System.out.println("1. Open");
						System.out.println("2. Completed");
						System.out.println("3. Cancelled");
						System.out.println("Choose status:");
						statusInput = sc.nextLine().trim();
						if (statusInput.equals("1")) {
							filterPackage = "OPEN";
							break;
						}
						else if (statusInput.equals("2")) {
							filterPackage = "COMPLETED";
							break;
						}
						else if (statusInput.equals("3")) {
							filterPackage = "CANCELLED";
							break;
						}
						else System.out.println("Invalid selection");
					}
					break;
					
				case "4":
					ArrayList<Project> projects = taskSystem.getProjects();
					while(true) {
						for (int i = 0; i<projects.size(); i++) {
							System.out.println((i+1) + ": " + projects.get(i).getName());
						}
						System.out.println("Enter project name: ");
						String projectName = sc.nextLine().trim();
						if (projectName.equals("")) break;
						if (taskSystem.getProjectByName(projectName) != null) {
							filterType = "Project";
							filterPackage = projectName;
							break;
						}
						else System.out.println("Project does not exist");
					}
					break;
					
				case "5":
					LocalDate startDate;
					LocalDate endDate;
					while(true) {
						try {
							System.out.println("Enter start date YYYY-MM-DD: ");
							startDate = LocalDate.parse(sc.nextLine().trim());
							System.out.println("Enter end date YYYY-MM-DD: ");
							endDate = LocalDate.parse(sc.nextLine().trim());
							if (endDate.isBefore(startDate)) {
								System.out.println("End date cannot be before start date");
							}
							else {
								filterType = "Date range";
								filterPackage = startDate + "," + endDate;
								break;
							}
						}
						catch(Exception e) {
							System.out.println("Invalid date format");
						}
					}
					break;
					
				case "6":
					while(true) {
						System.out.println("Enter tag name (Press enter to leave blank): ");
						String tagName = sc.nextLine().trim();
						if (tagName.equals("")) break;
						if (taskSystem.getTagByName(tagName) != null) {
							filterType = "Tag";
							filterPackage = tagName;
							break;
						}
						else System.out.println("Tag does not exist");
					}
					break;
					
				case "7":
					filterType = "None";
					filterPackage = "";
					break;
				}
				break;
				
			case "3":
				String sortInput;
				while(true) {
					System.out.println("Available sort types:");
					System.out.println("1. Due date");
					System.out.println("2. Priority");
					System.out.print("Select an option:");
					sortInput = sc.nextLine().trim();
					if (sortInput.equals("1") || sortInput.equals("2")) break;
					System.out.println("Invalid selection");
				}
				
				if (sortInput.equals("1")) sortType = "Due date";
				else sortType = "Priority";
				
				while(true) {
					System.out.println("1. Ascending");
					System.out.println("2. Descending");
					System.out.print("Select an option:");
					String orderInput = sc.nextLine().trim();
					if (orderInput.equals("1")) {
						sortOrder = "Ascending";
						break;
					}
					else if (orderInput.equals("2")) {
						sortOrder = "Descending";
						break;
					}
					else System.out.println("Invalid selection");
				}
				break;
				
			case "4":
				return;
			}
		}
	}
	
}