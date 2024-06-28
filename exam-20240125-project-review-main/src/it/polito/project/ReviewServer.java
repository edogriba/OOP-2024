package it.polito.project;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ReviewServer {

	private Map<String, Group> groups;
	private Map<String, Review> reviews;	
	private int numRev;


	public ReviewServer() {
		groups = new HashMap<>();
		reviews = new HashMap<>();
		numRev = 0;
	}

	/**
	 * adds a set of student groups to the list of groups
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param groups the project groups
	 */
	public void addGroups(String... groups) {
		for (String g : groups) {
			if (!this.groups.containsKey(g)) {
				this.groups.put(g, new Group(g));
			}
		}
		
	}

	/**
	 * retrieves the list of available groups
	 * 
	 * @return list of groups
	 */
	public Collection<String> getGroups() {
		return groups.keySet();
	}
	
	
	/**
	 * adds a new review with a given group
	 * 
	 * @param title		title of review
	 * @param topic	subject of review
	 * @param group  group of the review
	 * @return a unique id of the review
	 * @throws ReviewException in case of non-existing group
	 */
	public String addReview(String title, String topic, String group) throws ReviewException {
		if (!groups.containsKey(group)) {
			throw new ReviewException();
		}
		numRev++;
		Review r = new Review(title, topic, group, String.valueOf(numRev));
		groups.get(group).addReview(r);
		reviews.put(r.getId(), r);
		return String.valueOf(numRev);
	}

	/**
	 * retrieves the list of reviews with the given group
	 * 
	 * @param group 	required group
	 * @return list of review ids
	 */
	public Collection<String> getReviews(String group) {
		return groups.get(group).getReviews().values().stream().map(Review::getId).collect(Collectors.toList());
	}

	/**
	 * retrieves the title of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the title
	 */
	public String getReviewTitle(String reviewId) {
		return reviews.get(reviewId).getName();
	}

	/**
	 * retrieves the topic of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the topic of the review
	 */
	public String getReviewTopic(String reviewId) {
		return reviews.get(reviewId).getTopic();
	}

	// R2
		
	/**
	 * Add a new option slot for a review as a date and a start and end time.
	 * The slot must not overlap with an existing slot for the same review.
	 * 
	 * @param reviewId	id of the review
	 * @param date		date of slot
	 * @param start		start time
	 * @param end		end time
	 * @return the length in hours of the slot
	 * @throws ReviewException in case of slot overlap or wrong review id
	 */
	public double addOption(String reviewId, String date, String start, String end) throws ReviewException {
		if (!reviews.containsKey(reviewId)) {
			throw new ReviewException();
		}
		if (reviews.get(reviewId).isThereSlot(date, start, end)) {
			throw new ReviewException();
		}
		return reviews.get(reviewId).createSlots(date, start, end);
	}

	/**
	 * retrieves the time slots available for a given review.
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-15:30".
	 * 
	 * @param reviewId		id of the review
	 * @return a map date -> list of slots
	 */
	public Map<String, List<String>> showSlots(String reviewId) {
		return reviews.get(reviewId).getSlots().stream().collect(Collectors.groupingBy(Slot::getDate, Collectors.mapping(Slot::getSlot, Collectors.toList())));
	}

	/**
	 * Declare a review open for collecting preferences for the slots.
	 * 
	 * @param reviewId	is of the review
	 */
	public void openPoll(String reviewId) {
		reviews.get(reviewId).setOpen();
	}


	/**
	 * Records a preference of a student for a specific slot/option of a review.
	 * Preferences can be recorded only for review for which poll has been opened.
	 * 
	 * @param email		email of participant
	 * @param name		name of the participant
	 * @param surname	surname of the participant
	 * @param reviewId	id of the review
	 * @param date		date of the selected slot
	 * @param slot		time range of the slot
	 * @return the number of preferences for the slot
	 * @throws ReviewException	in case of invalid id or slot
	 */
	public int selectPreference(String email, String name, String surname, String reviewId, String date, String slot) throws ReviewException {
		if (!reviews.containsKey(reviewId)) {
			throw new ReviewException();
		}
		Review r = reviews.get(reviewId);
		if (!r.hasSlot(slot) || !r.getOpen()) {
			throw new ReviewException();
		}
		r.addPref(date+"T"+slot, new Preference(email, name, surname, reviewId, date, slot));
		return r.getPrefSlot(date+"T"+slot);
	}

	/**
	 * retrieves the list of the preferences expressed for a review.
	 * Preferences are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=EMAIL", including date, time interval, and email separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId review id
	 * @return list of preferences for the review
	 */
	public Collection<String> listPreferences(String reviewId) {
		return reviews.get(reviewId).getPref().stream().map(Preference::toString).collect(Collectors.toList());
	}

	/**
	 * close the poll associated to a review and returns
	 * the most preferred options, i.e. those that have receive the highest number of preferences.
	 * The options are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=##", including date, time interval, and preference count separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId	id of the review
	 */
	public Collection<String> closePoll(String reviewId) {
		reviews.get(reviewId).closePoll();
		return reviews.get(reviewId).getMaxPref();
	}

	
	/**
	 * returns the preference count for each slot of a review
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots with preferences described as strings with the format 
	 * "hh:mm-hh:mm=###", including the time interval and the number of preferences 
	 * e.g. "14:00-15:30=2".
	 * 
	 * All possible dates are reported and for each date only 
	 * the slots with at least one preference are listed.
	 * 
	 * @param reviewId	the id of the review
	 * @return the map data -> slot preferences
	 */
	public Map<String, List<String>> reviewPreferences(String reviewId) {
		return reviews.get(reviewId).getPref().stream().collect(Collectors.groupingBy(Preference::getDate, Collectors.mapping(p->(p.getS() + "=" + String.valueOf(reviews.get(reviewId).getPrefSlot(p.getDate() + "T" +p.getS()))), Collectors.toList()))).entrySet().stream()
        .collect(Collectors.toMap(
			Map.Entry::getKey,
			entry -> entry.getValue().stream().distinct().collect(Collectors.toList())));
	}


	/**
	 * computes the number preferences collected for each review
	 * The result is a map that associates to each review id the relative count of preferences expressed
	 * 
	 * @return the map id : preferences -> count
	 */
	public Map<String, Integer> preferenceCount() {
		return reviews.values().stream().collect(Collectors.toMap(Review::getId, Review::getNumPref));
	}
}
