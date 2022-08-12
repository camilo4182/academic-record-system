package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class RequestBodyStudentDTO {

    private String id;
    private String name;
    private String email;
    private String careerId;
    private float averageGrade;

}
