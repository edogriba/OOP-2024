package jobOffers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Candidate {

    private String name;
    private List<String> skills;
    private List<String> applications;
    private Map<String, Integer> ratings;

    public Candidate(String name, List<String> skills) {
        this.name = name;
        this.skills = skills;
        applications = new LinkedList<>();
        ratings = new HashMap<>();
    }

    public String getName() {
        return name;
    }
    public List<String> getSkills() {
        return skills;
    }
    public void addApplication(String a) {
        applications.add(a);
    }

    public List<String> getApplications() {
        return applications;
    }

    public Map<String, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Integer> m) {
        this.ratings = m;
    }



    

}   
