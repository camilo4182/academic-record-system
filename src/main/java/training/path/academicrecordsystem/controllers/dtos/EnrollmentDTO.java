package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class EnrollmentDTO {

    private String id;
    private StudentDTO student;
    private CourseClassDTO courseClass;
    private CareerDTO career;
    private int semester;

}
