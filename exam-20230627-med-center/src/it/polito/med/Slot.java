package it.polito.med;

public class Slot {

    private String id;
    private String date;
    private String slot;

    public Slot(String id, String date, String slot) {
        this.id = id;
        this.date = date;
        this.slot = slot;
    }

	public String getId() {
		return id;
	}
	public String getDate() {
		return date;
	}
	public String getSlot() {
        return slot;
    }

}
