package it.polito.oop.elective;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {

    private Map<String, Course> courses;
    private Map<String, Student> students;
    private List<Notifier> notifiers;

    public ElectiveManager() {
        courses = new HashMap<>();
        students = new HashMap<>();
        notifiers = new LinkedList<>();
    }

    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
        courses.put(name, new Course(name, availablePositions));
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
        return courses.keySet().stream().collect(Collectors.toCollection(TreeSet::new));
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, double gradeAverage){
        if (students.containsKey(id)) {
            students.get(id).setAvg(gradeAverage);
        }
        students.put(id, new Student(id, gradeAverage));
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return students.keySet();
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
        return students.values().stream().filter(s->s.getAvg() >= inf && s.getAvg() <= sup).map(Student::getId).collect(Collectors.toList());
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
        if (!students.containsKey(id)) {
            throw new ElectiveException();
        }
        int numReq = courses.size();
        if (numReq > 3 || numReq < 1) {
            throw new ElectiveException();
        }
        List<Course> l = new LinkedList<>();
        for (String c : courses) {
            if (!this.courses.containsKey(c)) {
                throw new ElectiveException();
            }
            else {
                l.add(this.courses.get(c));
            }

        }
        students.get(id).setReq(l, numReq);
        for (Notifier n : notifiers) {
            n.requestReceived(id);
        }

        return numReq;
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
        Map<String,List<Long>> m = new HashMap<>();
        for (String c : courses.keySet()) {
            List<Long> l = new LinkedList<>();
            l.add(0L);
            l.add(0L);
            l.add(0L);
            m.put(c, l);
        }
        for (Student s : students.values()) {
            for (int i=0; i<s.getNumReq(); i++) {
                //m.compute(s.getChoice(i), (k, v) -> v== null ? v = Arrays.asList(0L, 0L, 0L) : (v.add(i, v.get(i) + 1L )) );
                List<Long> l1 = m.get(s.getChoice(i).getName());
                List<Long> l2 = new LinkedList<>();
                if (i == 0) {
                    l2.add(l1.get(0)+1L);
                    l2.add(l1.get(1));
                    l2.add(l1.get(2));
                }
                if (i == 1) {
                    l2.add(l1.get(0));
                    l2.add(l1.get(1)+1L);
                    l2.add(l1.get(2));
                }
                if (i == 2) {
                    l2.add(l1.get(0));
                    l2.add(l1.get(1));
                    l2.add(l1.get(2)+1L);
                }

                m.put(s.getChoice(i).getName(), l2);
                //l.replaceItem(l.get(i) + 1L, i);
            }
        }
        return m;
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
        int count =0;
        for (Student s : students.values().stream().sorted(Comparator.comparing(Student::getAvg).reversed()).collect(Collectors.toList())) {
            boolean found = false;
            for (int i =0; i<s.getNumReq() && !found; i++) {
                if (!s.getChoice(i).isFull()) {
                    found = true;
                    s.getChoice(i).addStudent(s);
                    s.setCourse(s.getChoice(i).getName());
                    for (Notifier n : notifiers) {
                        n.assignedToCourse(s.getId(), s.getCourse());
                    }
                }
            }
            if (!found) {
                count++;
            }
        }
        return count;
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
        Map<String, List<String>> m = courses.values().stream().map(Course::getStudents).flatMap(Collection::stream).sorted(Comparator.comparing(Student::getAvg).reversed()).collect(Collectors.groupingBy(Student::getCourse, Collectors.mapping(Student::getId, Collectors.toList())));
        for (Course c : courses.values()) {
            if (c.getNumStudents() == 0) {
                List<String> l = new LinkedList<>();
                m.put(c.getName(), l);
            }
        }
        return m;
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
        notifiers.add(listener);
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
        return (double) courses.values().stream().map(Course::getStudents).flatMap(Collection::stream).filter(s->s.getNumReq() >= (choice)).filter(s->s.getChoice(choice-1).getName().equals(s.getCourse())).count()/(double) students.values().stream().count();
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return students.values().stream().filter(s->s.getCourse() == null).map(Student::getId).collect(Collectors.toList());
    }
    
    
}
