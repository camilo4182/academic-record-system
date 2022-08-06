package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Career {

    private String id;
    private String name;
    private Set<Course> courses;

    public Career() {
        courses = new HashSet<>();
    }

    @Builder
    public Career(String id, String name, Set<Course> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public List<Course> getCourses() {
        return courses.stream().toList();
    }
}
