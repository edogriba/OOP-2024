package it.polito.ski;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Parking {
    private String name;
    private int capacity;
    private List<Lift> lifts;

    public Parking(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
        lifts = new LinkedList<>();
	}   
    

	public String getName() {
		return name;
	}
	public int getCapacity() {
		return capacity;
	}

    public void addLift(Lift l) {
        lifts.add(l);
    }

    public List<Lift> getLifts() {
        return lifts;
    }

    public boolean isProportionate() {
        int totCapacityLifts = lifts.stream().collect(Collectors.summingInt(Lift::getCapacity));
        if (capacity/totCapacityLifts < 30.0) {
            return true;
        }
        return false;
    }
	
}
