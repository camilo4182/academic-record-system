package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class RequestBodyEnrollmentDTO {

    private String id;
    private String studentId;
    private String courseClassId;
    private int semester;

}
