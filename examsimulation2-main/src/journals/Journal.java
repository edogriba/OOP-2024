package journals;

import java.util.Map;
import java.util.HashMap;


public class Journal {
    private String name;
    private double impactFactor;
    private Map<String, Paper> papers;
    private int numPapers;

	public Journal(String name, double impactFactor) {
        this.name = name;
        this.impactFactor = impactFactor;
        papers = new HashMap<>();
        numPapers = 0;
    }

	public String getName() {
		return name;
	}
	public double getImpactFactor() {
		return impactFactor;
	}

    public Map<String, Paper> getPapers() {
		return papers;
	}

    public void addPaper(Paper p) {
        papers.put(p.getPaperTitle(), p);
        numPapers++;
    }
    
    public int getNumPapers() {
        return numPapers;
    }

}
