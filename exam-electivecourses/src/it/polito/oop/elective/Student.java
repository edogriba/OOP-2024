package it.polito.oop.elective;

import java.util.LinkedList;
import java.util.List;

public class Student {
    private String id;
    private double avg;
    private int numReq;
    private List<Course> reqCourses;
    private String c;

	public Student(String id, double avg) {
		this.id = id;
		this.avg = avg;
        numReq = 0;
        reqCourses = new LinkedList<>();
        c = null;
	}

	public String getId() {
		return id;
	}
	public double getAvg() {
		return avg;
	}

    public double getNumReq() {
		return numReq;
	}

	public List<Course> getReqCourses() {
		return reqCourses;
	}

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public void setReq(List<Course> reqCourses, int numReq ) {
        this.numReq = numReq;
        this.reqCourses = reqCourses;
    }


    public void setCourse(String c) {
        this.c = c;
    }

    public String getCourse() {
        return c;
    }


    public Course getChoice (int index) {
        return reqCourses.get(index);
    }


}
