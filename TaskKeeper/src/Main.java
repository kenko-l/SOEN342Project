import java.io.*;
import java.util.*;


public class Main {
	static TaskSystem taskSystem = new TaskSystem();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		String UserInput;
		while(true) {
			displayMain();
			UserInput = sc.nextLine();
			//Input Sanitization
			switch(UserInput) {
			case "1":
				handleTaskCreation();
				break;
			case "2":
				handleTaskView();
				break;
			case "3":
				handleTaskSearch();
			case "4":
				try {
					taskSystem.importFromCSV("tasks.csv");
				} catch (IOException e) {
					System.out.println("Error!");
				}
				break;
			case "5":
				try {
					taskSystem.exportToCSV("tasks.csv");
				} catch (IOException e) {
					System.out.println("Error!");
				}
				break;
			default:
				break;
			}
				 
		}
	}
	
	public static void clearScreen() {
	}
	
	private static void displayMain() {
		System.out.println("1. Create Task");
		System.out.println("2. View tasks");
		System.out.println("3. Search tasks");
		System.out.println("4. Import from CSV");
		System.out.println("5. Export to CSV");
	}
	
	private static void handleTaskCreation() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter task name: ");
		String taskName = sc.nextLine();
		//Input sanitization
		clearScreen();
		System.out.print("Enter task description (Press enter to leave blank): ");
		String taskDescription = sc.nextLine();
		//Input sanitization
		clearScreen();
		System.out.println("Choose priority level:");
		System.out.println("1. Low");
		System.out.println("2. Medium");
		System.out.println("3. High");
		String taskPriority = sc.nextLine();
		//Input sanitization
		clearScreen();
		System.out.println("Enter task due date YYYY-MM-DD (Press enter to leave blank): ");
		String taskDueDate = sc.nextLine();
		//Input sanitization
		taskSystem.createTask(taskName, taskDescription, taskDueDate, taskPriority);
		clearScreen();
		System.out.println("Task created successfully!");
		clearScreen();
	}
	
	public static void handleTaskView() {
		System.out.println("Select sort method: ");
		System.out.println("1. Due Date");
		System.out.println("2. Priority");
		System.out.println("3. Status");
		String sortMethod = sc.nextLine();
		System.out.println("1. Ascending");
		System.out.println("2. Descending");
		String sortOrder = sc.nextLine();
		ArrayList<Task> tasks = taskSystem.getTasks(sortMethod);
		if (sortOrder.equals("2")) Collections.reverse(tasks);
		System.out.println("----------------------");
		for(int i = 0; i<tasks.size(); i++) {
			System.out.println((i+1) + ":\n" + tasks.get(i));
			System.out.println("----------------------");
		}
	}
	
	public static void handleTaskSearch() {
		ArrayList<Task> tasks;
		System.out.println("Select search method: ");
		System.out.println("1. Keyword search");
		System.out.println("2. Date range");
		String searchMethod = sc.nextLine();
		if (searchMethod.equals("1")) {
			System.out.println("Enter keyword: ");
			String keyword = sc.nextLine();
			tasks = taskSystem.searchTasksKeyword(keyword);
		}
		else {
			System.out.println("Enter start date (YYYY-MM-DD): ");
			String startDate = sc.nextLine();
			System.out.println("Enter end date (YYYY-MM-DD): ");
			String endDate = sc.nextLine();
			tasks = taskSystem.searchTasksDates(startDate, endDate);
		}
		System.out.println("----------------------");
		for(int i = 0; i<tasks.size(); i++) {
			System.out.println((i+1) + ":\n" + tasks.get(i));
			System.out.println("----------------------");
		}
	}
	
	
	
}
