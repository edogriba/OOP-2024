package it.polito.oop.elective;

import java.util.LinkedList;
import java.util.List;

public class Course {
    
    private String name;
    private int avPlaces;
    private List<Student> students;
    private int numStudents;

	public Course(String name, int avPlaces) {
        this.name = name;
        this.avPlaces = avPlaces;
        numStudents = 0;
        students = new LinkedList<>();
    }

    public String getName() {
		return name;
	}

    public int getNumStudents() {
        return numStudents;
    }

	public int getAvPlaces() {
		return avPlaces;
	}

	public List<Student> getStudents() {
		return students;
	}

    public boolean isFull() {
        return avPlaces == numStudents;
    }

    public void addStudent(Student s) {
        numStudents++;
        students.add(s);
    }

}
