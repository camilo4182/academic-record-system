package training.path.academicrecordsystem.controllers.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseClassDTO {

    private String id;
    private boolean available;
    private String professorId;
    private String professorName;
    private String courseId;
    private String courseName;

    public CourseClassDTO() {
    }

    @Builder
    public CourseClassDTO(String id, boolean available, String professorId, String professorName, String courseId, String courseName) {
        this.id = id;
        this.available = available;
        this.professorId = professorId;
        this.professorName = professorName;
        this.courseId = courseId;
        this.courseName = courseName;
    }
}
