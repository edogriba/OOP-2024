package journals;

import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
//import static java.util.stream.Collection;


public class Journals {

	private Map<String, Journal> journals;
	private Map<String, Author> authors;

	private int numAuthors;

	public Journals() {
		journals = new HashMap<>();
		authors = new HashMap<>();
		numAuthors = 0;
	}
	
	//R1 
	/**
	 * inserts a new journal with name and impact factor. 
	 * 
	 * @param name	name of the journal
	 * @param impactFactor relative impact factor
	 * @return  the number of characters of the name
	 * @throws JException if the journal (represented by its name) already exists
	 */
	public int addJournal (String name, double impactFactor) throws JException {
		if (journals.containsKey(name)) {
			throw new JException();
		}
		journals.put(name, new Journal(name, impactFactor));
		return name.length();
	}

	/**
	 * retrieves the impact factor of the journal indicated by the name
	 * 
	 * @param name the journal name
	 * @return the journal IF
	 * @throws JException if the journal does not exist
	 */
	public double getImpactFactor (String name) throws JException {
		if (!journals.containsKey(name)) {
			throw new JException();
		}
		return journals.get(name).getImpactFactor();
	}

	/**
	 * groups journal names by increasing impact factors. 
	 * Journal names are listed in alphabetical order
	 * 
	 * @return the map of IF to journal
	 */
	public SortedMap<Double, List<String>> groupJournalsByImpactFactor () {
		return journals.values().stream().sorted(Comparator.comparing(Journal::getName)).collect(groupingBy(Journal::getImpactFactor, TreeMap::new, mapping(Journal::getName, toList())));
	}

	//R2
	/**
	 * adds authors. 
	 * Duplicated authors are ignored.
	 * 
	 * @param authorNames names of authors to be added
	 * @return the number of authors entered so far
	 */
	public int registerAuthors (String... authorNames) {

		for (String a : authorNames) {
			if (!authors.containsKey(a)) {
				authors.put(a, new Author(a));
				numAuthors++;
			}
		}
		return numAuthors;
	}
	
	/**
	 * adds a paper to a journal. 
	 * The journal is indicated by its name; 
	 * the paper has a title that must be unique in the specified journal,
	 * the paper can have one or more authors.
	 * 
	 * @param journalName
	 * @param paperTitle
	 * @param authorNames
	 * @return the journal name followed by ":" and the paper title.
	 * @throws JException if the journal does not exist or the title is not unique within the journal or not all authors have been registered
	 */
	public String addPaper (String journalName, String paperTitle, String... authorNames) throws JException {
		if (!journals.containsKey(journalName)) {
			throw new JException();
		}
		List<Author> aList = new LinkedList<>();
		for (String a : authorNames) {
			if (!authors.containsKey(a)){
				throw new JException();
			}
			else {
				Author aut = authors.get(a);
				aList.add(aut);
				
			}
		}
		if (journals.get(journalName).getPapers().containsKey(paperTitle)) {
			throw new JException();
		}
		Paper p =new Paper(journalName, paperTitle, aList);
		for (String a : authorNames) {
			authors.get(a).addPaper(p);
		}
		journals.get(journalName).addPaper(p);
		return journalName+":"+paperTitle;
	}
	
	/**
	 * gives the number of papers for each journal. 
	 * Journals are sorted alphabetically. 
	 * Journals without papers are ignored.
	 * 
	 * @return the map journal to count of papers
	 */
	public SortedMap<String, Integer> giveNumberOfPapersByJournal () { //serve toMap
		return journals.values().stream().filter(j->j.getNumPapers() > 0).collect(toMap(j->j.getName(), b->b.getNumPapers(), (oldValue,newValue)->oldValue, TreeMap::new));
	}
	
	//R3
	/**
	 * gives the impact factor for the author indicated.
	 * The impact factor of an author is obtained by adding 
	 * the impact factors of his/her papers. 
	 * The impact factor of a paper is equal to that of the 
	 * journal containing the paper.
	 * If the author has no papers the result is 0.0.
	 *
	 * @param authorName
	 * @return author impact factor
	 * @throws JException if the author has not been registered
	 */
	public double getAuthorImpactFactor (String authorName) throws JException {
		if (!authors.containsKey(authorName)) {
			throw new JException();
		}
		if (authors.get(authorName).getPapers().isEmpty()) {
			return 0.0;
		}
		return authors.get(authorName).getPapers().stream().map(p->journals.get(p.getJournalName())).collect(summingDouble(Journal::getImpactFactor));
		//return authors.get(authorName).getPapers().stream().map(p->journals.get(p.getJournalName())).collect(summingDouble(Journal::getImpactFactor));
	}
	
	/**
	 * groups authors (in alphabetical order) by increasing impact factors.
	 * Authors without papers are ignored.
	 * 
	 * @return the map IF to author list
	 */
	public SortedMap<Double, List<String>> getImpactFactorsByAuthors () {
		for (Author a : authors.values()) {
			try {
				a.setImpactFactor(getAuthorImpactFactor(a.getName()));
			}
			catch (JException je) {
				a.setImpactFactor(0);
			}
		}
		return authors.values().stream().filter(a->a.getImpactFactor() > 0.0 ).sorted(Comparator.comparing(Author::getName)).collect(groupingBy(Author::getImpactFactor , TreeMap::new, mapping(Author::getName, toList())));
		
	}
	
	
	//R4 
	/**
	 * gives the number of papers by author; 
	 * authors are sorted alphabetically. 
	 * Authors without papers are ignored.
	 * 
	 * @return
	 */
	public SortedMap<String, Integer> getNumberOfPapersByAuthor() {
		return authors.values().stream().filter(a->!a.getPapers().isEmpty()).collect(toMap(Author::getName, Author::getNumPapers, (oldV, newV)-> oldV, TreeMap::new));
	}
	
	/**
	 * gives the name of the journal having the largest number of papers.
	 * If the largest number of papers is common to two or more journals 
	 * the result is the name of the journal which is the first in 
	 * alphabetical order.
	 * 
	 * @return journal with more papers
	 */
	public String getJournalWithTheLargestNumberOfPapers() {
		Optional<String> jOpt = journals.values().stream().sorted(Comparator.comparing(Journal::getNumPapers).reversed().thenComparing(Journal::getName)).map(j->j.getName() +":"+ j.getNumPapers()).findFirst();
		return jOpt.isPresent() ? jOpt.get() : null;
	}

}

