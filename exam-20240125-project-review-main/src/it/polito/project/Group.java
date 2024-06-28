package it.polito.project;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class Group {
    private String name;
    private Map<String, Review> reviews;

    public Group(String name) {
        this.name = name;
        reviews = new HashMap<>();
    }

    public void addReview(Review r) {
        reviews.put(r.getId(), r);
    }

    public Map<String, Review> getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }
}
