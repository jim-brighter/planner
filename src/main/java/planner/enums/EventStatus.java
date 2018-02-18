package planner.enums;

public enum EventStatus {
	
	TO_DO("To Do"),
	COMPLETE("Complete");
	
	private String desc;
	
	private EventStatus(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
