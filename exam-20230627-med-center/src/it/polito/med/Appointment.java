package it.polito.med;

public class Appointment {

    private String id;
    private String ssn;
    private String name;
    private String surname;
    private String medId;
    private String date;
    private String s;
    private boolean completed;
    
    public String getId() {
        return id;
    }

    public String getSsn() {
        return ssn;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMedId() {
        return medId;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return s.split("-")[0];
    }
    public String getS() {
        return s;
    }
    public void markCompleted() {
        this.completed = true;
    }
    public boolean getCompleted() {
        return completed;
    }

    public Appointment(String id, String ssn, String name, String surname, String medId, String date, String s) {
        this.id = id;
        this.ssn = ssn;
        this.name = name;
        this.surname = surname;
        this.medId = medId;
        this.date = date;
        this.s = s;
        completed = false;
    }

    @Override 
    public String toString() {
        return getStart() + "=" + ssn;
    }


}
