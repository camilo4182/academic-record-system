package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseClass {

    private String id;
    private boolean available;
    private Professor professor;
    private Course course;

    public CourseClass() {
    }

    @Builder
    public CourseClass(String id, boolean available, Professor professor, Course course) {
        this.id = id;
        this.available = available;
        this.professor = professor;
        this.course = course;
    }

}
