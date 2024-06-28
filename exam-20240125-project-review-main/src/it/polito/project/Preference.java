package it.polito.project;

public class Preference {
    private String email;
    private String name;
    private String surname;
	private String revId;
    private String date;
    private String s;

	public Preference(String email, String name, String surname, String revId, String date, String s) {
		this.email = email;
		this.name = name;
        this.surname = surname;
		this.revId = revId;
		this.date = date;
		this.s = s;
	}

	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
    public String getSurname() {
		return surname;
	}
	public String getRevId() {
		return revId;
	}
	public String getDate() {
		return date;
	}
	public String getS() {
		return s;
	}
    @Override
    public String toString() {
        return date+"T"+s+"="+email;
    }


}
