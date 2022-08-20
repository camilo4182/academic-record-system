package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnCreate;

import javax.validation.constraints.*;

@Data
public class ProfessorDTO {

    @Null(message = "Don't provide id for professor", groups = OnCreate.class)
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "Professor name is mandatory")
    @Size(min = 4, message = "Professor name must have at least 4 characters")
    private String name;

    @NotBlank(message = "Professor email is mandatory")
    @Email(message = "Professor email must be a well-formed email address")
    private String email;

    @Min(value = 1000, message = "Professor salary must be greater than 1000")
    @Max(value = 100000, message = "Professor salary cannot exceed 100000")
    private float salary;

}
