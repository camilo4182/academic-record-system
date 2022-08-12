package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseClass {

    private String id;
    private int capacity;
    private int enrolledStudents;
    private boolean available;
    private Professor professor;
    private Course course;

    public CourseClass() {
    }

    @Builder
    public CourseClass(String id, int capacity, int enrolledStudents, boolean available, Professor professor, Course course) {
        this.id = id;
        this.capacity = capacity;
        this.enrolledStudents = enrolledStudents;
        this.available = available;
        this.professor = professor;
        this.course = course;
    }

    public void increaseEnrolledStudents() {
        enrolledStudents++;
        if (enrolledStudents == capacity) available = false;
    }

}
