package it.polito.oop.books;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Topic implements Comparable<Topic>{

	private String keyword;
	private List<Topic> subtopics;

	public Topic(String keyword) {
		this.keyword = keyword;
		subtopics = new LinkedList<>();
	} 

	@Override
	public int compareTo(Topic o) {
		return keyword.compareTo(o.keyword);
	}


	public String getKeyword() {
        return keyword;
	}
	
	@Override
	public String toString() {
	    return keyword;
	}

	public boolean addSubTopic(Topic topic) {
		if (!subtopics.contains(topic)) { // controllare questo forse non giustissimo
			subtopics.add(topic);
			return true;
		}
        return false;
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        return subtopics.stream().sorted().collect(Collectors.toList());
	}

	public List<Topic> getSubTopicsRec() {
		List<Topic> l = new LinkedList<>();
		this.getSubR(l);
		return l.stream().sorted().collect(Collectors.toList());
	}

	public void getSubR(List<Topic> l) {
		l.add(this);
		for (Topic tt : subtopics) {
			tt.getSubR(l);
		}
	}
}
