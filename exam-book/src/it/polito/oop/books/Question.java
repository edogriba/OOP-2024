package it.polito.oop.books;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Question {

	private String question;
	private Topic t;
	private Map<String, Boolean> answers;

	public Question(String question, Topic t) {
		this.question = question;
		this.t = t;
		answers = new HashMap<>();
	}
	
	public String getQuestion() {
		return question;
	}
	
	public Topic getMainTopic() {
		return t;
	}

	public void addAnswer(String answer, boolean correct) {
		answers.put(answer, correct);
	}
	
    @Override
    public String toString() {
        return question + " (" + t + ")";
    }

	public long numAnswers() {
	    return answers.values().stream().count();
	}

	public Set<String> getCorrectAnswers() {
		return answers.entrySet().stream().filter(a->a.getValue()).map(Map.Entry::getKey).collect(Collectors.toSet());
	}

	public Set<String> getIncorrectAnswers() {
		return answers.entrySet().stream().filter(a->!a.getValue()).map(Map.Entry::getKey).collect(Collectors.toSet());
	}

	public List<String> getAnswers() {
		return answers.keySet().stream().collect(Collectors.toList());
	}
}
