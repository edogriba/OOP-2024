package journals;

import java.util.List;
import java.util.LinkedList;


public class Author {

    private String name;
    private List<Paper> papers;
    private double impactFactor;
    private int numPapers;

    public Author(String name) {
        this.name = name;
        papers = new LinkedList<>();
        numPapers = 0;
    }

	public String getName() {
		return name;
	}


    public void addPaper(Paper p) {
        papers.add(p);
        numPapers++;
    }

    public int getNumPapers() {
        return numPapers;
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setImpactFactor(double d) {
        impactFactor = d;
    }

    public double getImpactFactor() {
        return impactFactor;
    }
}
