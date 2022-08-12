package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class ResponseBodyStudentDTO {

    private String id;
    private String name;
    private String email;
    private CareerDTO career;
    private float averageGrade;

}
