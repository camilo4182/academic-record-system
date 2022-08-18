package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import java.util.List;

@Data
public class RequestBodyEnrollmentDTO {

    private String id;
    private String studentId;
    private List<@UUIDValidator String> courseClassIds;
    private int semester;

}
