package it.polito.oop.books;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class ExerciseChapter {
    private String title;
    private int numPages;
    private List<Question> questions;

    public ExerciseChapter(String title, int numPages) {
        this.title = title;
        this.numPages =numPages;
        questions = new LinkedList<>();
    }

    public List<Topic> getTopics() {
        return questions.stream().map(Question::getMainTopic).distinct().sorted().collect(Collectors.toList());
	};
	

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
    

	public void addQuestion(Question question) {
        questions.add(question);
	}	
}
