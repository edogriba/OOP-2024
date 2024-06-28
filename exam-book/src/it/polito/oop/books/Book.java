package it.polito.oop.books;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Book {

	private Map<String, Topic> topics;
	private List<Question> questions;
	private List<TheoryChapter> tcs;
	private List<ExerciseChapter> ecs;
	private List<Assignment> assignments;

	public Book() {
		topics = new HashMap<>();
		questions = new LinkedList<>();
		tcs= new LinkedList<>();
		ecs = new LinkedList<>();
		assignments = new LinkedList<>();
	}
    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	public Topic getTopic(String keyword) throws BookException {
		if (keyword == null || keyword.equals("")) {
			throw new BookException();
		}

		if (topics.containsKey(keyword)) {
			return topics.get(keyword);
		}
		else {
			Topic t = new Topic(keyword);
			topics.put(keyword,t);
			return t;
		}
	}

	public Question createQuestion(String question, Topic mainTopic) {
		Question q = new Question(question, mainTopic);
		questions.add(q);
        return q;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
		TheoryChapter tc = new TheoryChapter(title, numPages, text);
		tcs.add(tc);
        return tc;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
		ExerciseChapter ec = new ExerciseChapter(title, numPages);
		ecs.add(ec);
        return ec;
	}
	public List<Topic> getAllTopicsTh() {
		return tcs.stream().map(TheoryChapter::getTopics).flatMap(Collection::stream).collect(Collectors.toList());
	}

	public List<Topic> getAllTopicsEx() {
		return ecs.stream().map(ExerciseChapter::getTopics).flatMap(Collection::stream).collect(Collectors.toList());
	}

	public List<Topic> getAllTopics() {
		List<Topic> lt = new LinkedList<>();
		lt.addAll(getAllTopicsTh());
		lt.addAll(getAllTopicsEx());
        return lt.stream().distinct().sorted().collect(Collectors.toList());
	}

	public boolean checkTopics() {
        return getAllTopicsTh().containsAll(getAllTopicsEx());
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
		Assignment a = new Assignment(ID, chapter);
		assignments.add(a);
        return a;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return questions.stream().collect(Collectors.groupingBy(q->q.numAnswers()));
    }
}
