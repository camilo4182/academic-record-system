package training.path.academicrecordsystem.controllers.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseClassDTO {

    private String id;
    private String courseId;
    private String courseName;
    private String professorId;
    private String professorName;
    private boolean available;

    public CourseClassDTO() {
    }

    @Builder
    public CourseClassDTO(String id, String courseId, String courseName, String professorId, String professorName, boolean available) {
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
        this.professorId = professorId;
        this.professorName = professorName;
        this.available = available;
    }
}
