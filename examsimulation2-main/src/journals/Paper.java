package journals;

import static java.util.Comparator.nullsLast;

import java.util.List;

public class Paper {
    private String journalName;
    private String paperTitle;
    private List<Author> authors;

    public Paper(String journalName, String paperTitle, List<Author> authors) {
        this.journalName = journalName;
        this.paperTitle = paperTitle;
        this.authors = authors;
    }

	public String getJournalName() {
		return journalName;
	}
	public String getPaperTitle() {
		return paperTitle;
	}
	public List<Author> getAuthors() {
		return authors;
	}



}
