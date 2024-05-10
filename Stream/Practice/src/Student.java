import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

class Student {

    private String name;
    private int age;
    private Optional<Integer> friendsNumber;
    private List<Course> courses;


    public Student(String name, int age, List<Course> courses, Optional<Integer> friendsNumber) {
        this.name = name;
        this.age = age;
        this.courses = new LinkedList<>();
        this.courses.addAll(courses);
        this.friendsNumber = friendsNumber;
    }

    public Optional<Integer> getFriendsNumber() {
        return this.friendsNumber;
    }

    public int getAge() {
        return this.age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }
    public Collection<Course> getCourses() {
        return this.courses;
    }
    @Override
    public String toString() {
        if (friendsNumber.isPresent()) {
            return getName() + ", " + getAge() + " has " + friendsNumber.get() + " friends";
        }
        else {
            return getName() + ", " + getAge();
        }
    }
}
