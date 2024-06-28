package it.polito.oop.futsal;

public class Booking {

    private int field;
    private int a;
    private String time;

    public Booking(int field, int a, String time) {
        this.field =field;
        this.a = a;
        this.time = time;
    }

    public int getField() {
        return field;
    }
    public int getAssociate() {
        return a;
    }
    public String getTime() {
        return time;
    }
    
    
}
