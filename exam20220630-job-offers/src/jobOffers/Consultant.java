package jobOffers;

import java.util.List;

public class Consultant {

    private String name;
    private List<String> skills;

    public Consultant(String name, List<String> skills) {
        this.name = name;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public List<String> getSkills() {
        return skills;
    }
    
    
}
