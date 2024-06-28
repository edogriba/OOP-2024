package it.polito.project;

public class Slot {


    private int startMinute;
    private int endMinute;
    private String slot;
    private String date;
    
    public Slot(int startMinute, int endMinute, String slot, String date) {
        this.startMinute = startMinute;
        this.endMinute = endMinute;
        this.slot = slot;
        this.date = date;
    }
    public String getDate() {
        return date;
    }

	public int getStartMinute() {
		return startMinute;
	}
	public int getEndMinute() {
		return endMinute;
	}
	public String getSlot() {
		return slot;
	}


}
