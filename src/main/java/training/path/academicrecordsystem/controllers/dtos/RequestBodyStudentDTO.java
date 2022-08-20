package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnCreate;

import javax.validation.constraints.*;

@Data
public class RequestBodyStudentDTO {

    @Null(message = "Don't provide id for student", groups = OnCreate.class)
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "Student name is mandatory")
    @Size(min = 4, message = "Student name must have at least 4 characters")
    private String name;

    @NotBlank(message = "Student email is mandatory")
    @Email(message = "Student email must be a well-formed email address")
    private String email;

    @NotBlank(message = "A student must be associated to a career")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String careerId;

    @Min(value = 0, message = "Grades must be between 0.0 and 5.0")
    @Max(value = 5, message = "Grades must be between 0.0 and 5.0")
    private float averageGrade;

}
