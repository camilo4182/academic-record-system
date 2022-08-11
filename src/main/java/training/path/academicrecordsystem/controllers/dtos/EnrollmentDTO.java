package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class EnrollmentDTO {

    private String id;
    private StudentDTO studentDTO;
    private CourseClassDTO courseClassDTO;
    private int semester;

}
