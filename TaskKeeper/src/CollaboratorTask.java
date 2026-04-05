import java.time.LocalDate;

public class CollaboratorTask extends Subtask{
	protected Collaborator collaborator;

	public CollaboratorTask(Collaborator collaborator, Task parentTask, String title, String description, LocalDate dueDate, Priority priority) {
		super(parentTask, title, description, dueDate, priority);
		this.collaborator = collaborator;
	}

	public Collaborator getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(Collaborator collaborator) {
		this.collaborator = collaborator;
	}

	public String toString() {
		return super.toString() + "\nCollaborator: " + this.collaborator.getName();
	}

}
