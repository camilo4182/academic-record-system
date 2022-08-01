package training.path.academicrecordsystem.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long id;
    private String name;
    private List<Course> courses;

    public User() {
        courses = new ArrayList<>();
    }

    public User(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public String toString() {
        return "User {" + "id=" + id + ", name='" + name + '\'' +'}';
    }
}
