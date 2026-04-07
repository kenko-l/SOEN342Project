import java.util.*;
import java.time.*;


public class RecurrenceRule {
	private String pattern;
	private String patternKey;
	private Task task;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public RecurrenceRule(String pattern, String patternKey, Task task, LocalDate startDate, LocalDate endDate) {
		this.pattern = pattern;
		this.patternKey = patternKey;
		this.task = task;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPatternKey() {
		return patternKey;
	}

	public void setPatternKey(String patternKey) {
		this.patternKey = patternKey;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
}
