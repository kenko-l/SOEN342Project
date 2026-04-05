import java.util.*;

public class Project {
	private String name;
	private String description;
	private ArrayList<Collaborator> collaborators;
	
	public Project(String name, String description) {
		this.name = name;
		this.description = description;
		this.collaborators = new ArrayList<Collaborator>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ArrayList<Collaborator> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(ArrayList<Collaborator> collaborators) {
		this.collaborators = collaborators;
	}
	
	public void addCollaborator(Collaborator collaborator) {
		this.collaborators.add(collaborator);
	}

	public String toString() {
		String output = ("Name: " + this.name +
						"\nDescription: " + this.description);
		
		if (!collaborators.isEmpty()) {
			output += "Collaborators: ";
			for (Collaborator collaborator : collaborators) {
				output += collaborator;
			}
		}
		return output;
	}
}
