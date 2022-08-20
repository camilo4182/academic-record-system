package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;
import training.path.academicrecordsystem.validations.groups.OnCreate;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class RequestBodyEnrollmentDTO {

    @Null(message = "Don't provide id for career", groups = OnCreate.class)
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @Null(message = "Don't provide id for career", groups = OnCreate.class)
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String studentId;

    @NotEmpty(message = "Enrollments must have at least one class registered")
    private List<@UUIDValidator String> classIds;

    @Min(value = 1, message = "Semester must be between 1 and 12")
    @Max(value = 12, message = "Semester must be between 1 and 12")
    private int semester;

}
