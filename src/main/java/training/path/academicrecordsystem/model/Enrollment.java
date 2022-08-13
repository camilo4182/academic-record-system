package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Enrollment {

    private String id;
    private int semester;
    private Student student;
    private Set<CourseClass> courseClasses;

    public Enrollment() {
        courseClasses = new HashSet<>();
    }

    @Builder
    public Enrollment(String id, int semester, Student student, Set<CourseClass> courseClasses) {
        this.id = id;
        this.semester = semester;
        this.student = student;
        this.courseClasses = courseClasses;
    }

    public void addClass(CourseClass courseClass) {
        courseClasses.add(courseClass);
    }

    public void remove(CourseClass courseClass) {
        courseClasses.remove(courseClass);
    }

    public List<CourseClass> getCourseClasses() {
        return courseClasses.stream().toList();
    }
}
