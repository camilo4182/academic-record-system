package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseClass {

    private boolean available;
    private Professor professor;
    private Course course;

    public CourseClass() {
    }

    @Builder
    public CourseClass(boolean available, Professor professor, Course course) {
        this.available = available;
        this.professor = professor;
        this.course = course;
    }

}
