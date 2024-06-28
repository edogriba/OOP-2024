package it.polito.project;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Review {

    private String name;
    private String topic;
    private String group;
    private String id;
    private List<Slot> slots;
    private boolean open;
    private Map<String, Integer> poll;
    private List<Preference> pref;
    private int numPref;

    public Review(String name, String topic, String group, String id) {
        this.name = name;
        this.topic = topic;
        this.group = group;
        this.id = id;
        slots = new LinkedList<>();
        open = false;
        poll = new HashMap<>();
        pref = new LinkedList<>();
        numPref = 0;
    }
    
    public void setOpen() {
        open = true;
    }

    public boolean getOpen() {
        return open;
    }
    public void closePoll() {
        open = false;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public String getGroup() {
        return group;
    }

    public String getId() {
        return id;
    }
    public int getNumPref() {
        return numPref;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public List<String> getSlotsName() {
        return slots.stream().map(Slot::getSlot).collect(Collectors.toList());
    }

    public List<Preference> getPref() {
        return pref;
    }

    public double createSlots(String date, String start, String end) {
        String[] startSplit = start.split(":");
        String[] endSplit = end.split(":");
        int startMin = Integer.parseInt(startSplit[0]) * 60 + Integer.parseInt(startSplit[1]);
        int endMin = Integer.parseInt(endSplit[0]) * 60 + Integer.parseInt(endSplit[1]);
        addSlot(new Slot(startMin, endMin, start +"-"+end, date));
        return (endMin-startMin)/60;
    }

    public boolean hasSlot(String str) {
        for (Slot s : slots) {
            if (s.getSlot().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void addSlot(Slot s) {
        slots.add(s);
    }

    public boolean isThereSlot(String date, String start, String end) {
        String[] startSplit = start.split(":");
        String[] endSplit = end.split(":");
        int startMin = Integer.parseInt(startSplit[0]) * 60 + Integer.parseInt(startSplit[1]);
        int endMin = Integer.parseInt(endSplit[0]) * 60 + Integer.parseInt(endSplit[1]);
        
        for (Slot s: slots) {
            if (s.getDate().equals(date)){
                if (startMin > s.getStartMinute()  && startMin < s.getEndMinute()) {
                    return true;
                }
                else if (s.getStartMinute() > startMin && endMin < s.getEndMinute()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addPref(String s, Preference p) {
        poll.compute(s, (k, v) -> v == null ? v=1 : (v = v+1));
        /*int value = 0;
        if (poll.containsKey(s)) {
            value = poll.get(s);
            poll.remove(s);
        }
        value++;
        poll.put(s, value);*/
        pref.add(p);
        numPref++;
    } 

    public int getPrefSlot(String s) {
        return poll.get(s);
    }

    public List<String> getMaxPref() {
        int max = 0;
        List<String> l = new LinkedList<>();
        for (Map.Entry<String, Integer> m : poll.entrySet()) {
            if (m.getValue() == max ) {
                l.add(m.getKey()+"="+m.getValue());
            }
            else if (m.getValue() > max)  {
                max = m.getValue();
                l.removeAll(l);
                l.add(m.getKey()+"="+m.getValue());
            }
        }
        return l;
    }
    
}
