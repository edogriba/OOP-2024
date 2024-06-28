package it.polito.med;

import java.util.LinkedList;
import java.util.List;

public class Doctor {

    private String id;
    private String name;
    private String surname;
    private String spec;
    private List<Slot> slots;
    private int numApp;

    public Doctor(String id, String name, String surname, String spec) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.spec = spec;
        slots = new LinkedList<>();
        numApp=0;
	}
    public void addApp() {
        numApp++;
    }
    public int getNumApp() {
        return numApp;
    }
    public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getSpec() {
		return spec;
	}

    private int fromStringToIntTime(String time) {
        String[] splittings = time.split(":");
        int hour = Integer.parseInt(splittings[0])*60;
        int minutes = Integer.parseInt(splittings[1]);
        return hour+minutes;
        
    }
    private String fromIntToStringTime(int time) {
        int hour = time / 60;
        int minutes = time % 60;
        String hourString =  String.valueOf(hour);
        String minutesString = String.valueOf(minutes);
        if (hour < 10) {
            hourString = "0" + hourString;
        }
        if (minutes<10) {
            minutesString = "0" + minutesString;
        }
        return hourString + ":" + minutesString;
    }

    public int addSlots(String date, String start, String end, int duration) {
        int count=0;
        int  startTime = fromStringToIntTime(start);
        int endTime = fromStringToIntTime(end);
        while (startTime <= endTime-duration) {
            count++;
            String startingSlotTime = fromIntToStringTime(startTime);
            startTime += duration;
            String endingSlotTime = fromIntToStringTime(startTime);
            slots.add(new Slot(id, date, startingSlotTime+"-"+endingSlotTime));
        }
        return count;
    }

    public List<Slot> getSlots() {
        return slots;
    }
    public double getRate() {
        return (double) numApp / slots.size();
    }
}
