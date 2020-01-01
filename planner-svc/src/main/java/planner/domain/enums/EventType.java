package planner.domain.enums;

public enum EventType {

	TO_DO("To Do"),
	TO_EAT("To Eat"),
	TO_COOK("To Cook"),
	TO_GO("To Go"),
	TO_WATCH("To Watch"),
	TO_BUY("To Buy");
	
	private String desc;
	
	private EventType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
}
