package it.polito.ski;

public class Type {
    private String name;
    private String category;
    private int capacity;
    
	public Type(String name, String category, int capacity) {
		this.name = name;
		this.category = category;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}
	
	public String getCategory() {
		return category;
	}
	public int getCapacity() {
		return capacity;
	}
	

}
