import java.util.*;

public class ProjectList {
	ArrayList<Project> projects;
	
	public ProjectList() {
		this.projects = new ArrayList<Project>();
	}
	
	public ArrayList<Project> getProjects() {
		return this.projects;
	}
	
	public void createProject(String name, String description) {
		Project newProject = new Project(name, description);
		this.projects.add(newProject);
	}
}
