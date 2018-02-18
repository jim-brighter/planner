package planner.enums;

public enum EventType {

	TO_DO("To Do"),
	TO_EAT("To Eat"),
	TO_COOK("To Cook");
	
	private String desc;
	
	private EventType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
}
