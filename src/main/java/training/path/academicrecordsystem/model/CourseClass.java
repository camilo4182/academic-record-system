package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.*;

@Data
public class CourseClass {

    @NotBlank(message = "Class id cannot be null")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @Min(value = 1, message = "A class must have at least a capacity of one student")
    @Max(value = 35, message = "A class can only have a maximum of 35 students")
    private int capacity;

    @Min(value = 0, message = "The amount of enrolled students cannot be a negative number")
    @Max(value = 35, message = "The amount of enrolled students cannot surpass the maximum capacity")
    private int enrolledStudents;

    private boolean available;

    @NotNull(message = "A class must be associated to a course")
    private Course course;

    @NotNull(message = "A class must have a professor associated to it")
    private Professor professor;

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

    public void decreaseEnrolledStudents() {
        enrolledStudents--;
        if (!available) available = true;
    }

}
