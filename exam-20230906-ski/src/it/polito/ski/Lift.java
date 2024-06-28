package it.polito.ski;

public class Lift {

    private String name;
    private Type t;
	public Lift(String name, Type t) {
		this.name = name;
		this.t = t;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return t.getName();
	}
    public int getCapacity() {
        return t.getCapacity();
    }


}
