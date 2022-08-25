package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnCreate;

import javax.validation.constraints.*;

@Data
public class RequestBodyEnrollmentDTO {

    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String studentId;

    private String classId;

    @Min(value = 1, message = "Semester must be between 1 and 12")
    @Max(value = 12, message = "Semester must be between 1 and 12")
    private int semester;

}
