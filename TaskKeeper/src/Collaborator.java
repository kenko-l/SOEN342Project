
public class Collaborator {
	private String name;
	private Category category;
	
	public Collaborator(String name, Category category) {
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String toString() {
		return ("Name: " + this.name + "\nCategory: " + this.category.toString());
	}
	
}
