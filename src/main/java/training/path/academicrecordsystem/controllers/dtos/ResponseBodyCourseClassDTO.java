package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class ResponseBodyCourseClassDTO {

    private String id;
    private CourseDTO course;
    private ProfessorDTO professor;
    private int capacity;
    private int enrolledStudents;
    private boolean available;

}
