package training.path.academicrecordsystem.controllers.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseClassDTO {

    private String id;
    private boolean available;
    private String professorId;
    private String courseId;

    public CourseClassDTO() {
    }

    @Builder
    public CourseClassDTO(String id, boolean available, String professorId, String courseId) {
        this.id = id;
        this.available = available;
        this.professorId = professorId;
        this.courseId = courseId;
    }
}
