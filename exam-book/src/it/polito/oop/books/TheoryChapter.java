package it.polito.oop.books;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class TheoryChapter {

    private String title;
    private int numPages;
    private String text;
    private List<Topic> topics;

    public TheoryChapter(String title, int numPages, String text) {
        this.title = title;
        this.numPages = numPages;
        this.text = text;
        topics = new LinkedList<>();
    }
    public String getText() {
		return text;
	}

    public void setText(String newText) {
        this.text = newText;
    }


	public List<Topic> getTopics() {
        return topics.stream().sorted().distinct().collect(Collectors.toList());
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public int getNumPages() {
        return numPages;
    }
    
    public void setNumPages(int newPages) {
        numPages = newPages;
    }
    
    public void addTopic(Topic topic) {
        topics.addAll(topic.getSubTopicsRec());

    }
    
}
