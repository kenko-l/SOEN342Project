import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class TaskSystem {
	private TaskList taskList;
	private ProjectList projectList;
	private TagList tagList;
	
	public TaskSystem() {
		this.taskList = new TaskList();
	}
	
	public void createTask(String taskName, String taskDescription, String taskPriority, String taskDueDate) {
		taskList.createTask(taskName, taskDescription, taskPriority, taskDueDate);
	}
	
	public ArrayList<Task> getTasks(String sortMethod) {
		ArrayList<Task> taskArray = this.taskList.getTasks();
		if (sortMethod.equals("1")) {
			taskArray.sort(byDueDate());
		}
		else if (sortMethod.equals("2")) {
			taskArray.sort(byPriority());
		}
		else if (sortMethod.equals("3")) {
			taskArray.sort(byStatus());
		}
		return taskArray;
	}
	
	public ArrayList<Task> searchTasksDates(String startDate, String endDate) {
	    ArrayList<Task> result = new ArrayList<>();

	    LocalDate start = LocalDate.parse(startDate);
	    LocalDate end = LocalDate.parse(endDate);

	    for (Task task : taskList.getTasks()) {
	        LocalDate due = task.getDueDate();
	        if (due != null &&
	            (due.isEqual(start) || due.isAfter(start)) &&
	            (due.isEqual(end) || due.isBefore(end))) {
	            result.add(task);
	        }
	    }

	    return result;
	}

	public ArrayList<Task> searchTasksKeyword(String keyword) {
	    ArrayList<Task> result = new ArrayList<>();
	    String lowerKeyword = keyword.toLowerCase();

	    for (Task task : taskList.getTasks()) {
	        String title = task.getTitle() == null ? "" : task.getTitle().toLowerCase();
	        String description = task.getDescription() == null ? "" : task.getDescription().toLowerCase();
	        if (title.contains(lowerKeyword) || description.contains(lowerKeyword)) {
	            result.add(task);
	        }
	    }
	    return result;
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

	private static int priorityValue(String p) {
	    switch (p) {
	        case "High": return 0;
	        case "Medium": return 1;
	        case "Low": return 2;
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

	private static int statusValue(String s) {
	    switch (s) {
	        case "Open": return 0;
	        case "In Progress": return 1;
	        case "Completed": return 2;
	        default: return 3;
	    }
	}
	//---------------------------------------------------
	//CSV Operations
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
	                writer.write(task.getPriority());
	                writer.write(",");
	                writer.write(task.getStatus());
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

	                Task task = new Task(title, description, dueDate, priority);

	                if (!creationDate.isEmpty()) {
	                    task.setCreationDate(LocalDate.parse(creationDate));
	                }

	                task.setStatus(status);
	                taskList.getTasks().add(task);
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
