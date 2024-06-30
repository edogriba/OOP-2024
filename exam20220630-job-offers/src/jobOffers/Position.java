package jobOffers;

import java.util.Map;

public class Position {
    
    private String role;
    private Map<String, Integer> reqSkills;

    public Position(Map<String, Integer> reqSkills, String role) {
        this.reqSkills = reqSkills;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Map<String, Integer> getReqSkills() {
        return reqSkills;
    }

    
}
