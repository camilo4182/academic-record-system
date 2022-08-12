package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class RequestBodyCourseClassDTO {

    private String id;
    private String courseId;
    private String professorId;
    private int capacity;
    private int enrolledStudents;
    private boolean available;

}
