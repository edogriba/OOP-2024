package it.polito.oop.futsal;

public class Option implements FieldOption {
    private int field;
    private int occupation;
    
    public Option (int field, int occupation) {
        this.field = field;
        this.occupation = occupation;

    }

    public int getField() {
        return field;
    }
    public int getOccupation() {
        return occupation;
    }
    
}
