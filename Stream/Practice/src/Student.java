import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class Student {

    private String name;
    private int age;
    private List<Course> courses;

    public Student(String name, int age, List<Course> courses) {
        this.name = name;
        this.age = age;
        this.courses = new LinkedList<>();
        this.courses.addAll(courses);
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
}
