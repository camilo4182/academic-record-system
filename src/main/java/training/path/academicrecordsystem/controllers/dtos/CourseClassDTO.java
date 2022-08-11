package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class CourseClassDTO {

    private String id;
    private CourseDTO course;
    private ProfessorDTO professor;
    private boolean available;

}
