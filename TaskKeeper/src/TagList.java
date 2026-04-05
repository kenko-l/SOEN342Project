import java.util.*;

public class TagList {
	private ArrayList<Tag> tagList;
	
	public TagList() {
		this.tagList = new ArrayList<Tag>();
	}
	
	public ArrayList<Tag> getTags() {
		return this.tagList;
	}
	
	public void createTag(String title) {
		Tag newTag = new Tag(title);
		this.tagList.add(newTag);
	}
}
