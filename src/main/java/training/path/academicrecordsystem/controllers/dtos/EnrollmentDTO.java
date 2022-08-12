package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class EnrollmentDTO {

    private String id;
    private RequestBodyStudentDTO student;
    private RequestBodyCourseClassDTO courseClass;
    private CareerDTO career;
    private int semester;

}
