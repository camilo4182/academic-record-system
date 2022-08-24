package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnCreate;

import javax.validation.constraints.*;

@Data
public class RequestBodyProfessorDTO {

    @Null(message = "Don't provide id for professor", groups = OnCreate.class)
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "User first name is mandatory")
    @Size(min = 3, message = "User first name must have at least 3 characters")
    private String firstName;

    @NotBlank(message = "User last name is mandatory")
    @Size(min = 3, message = "User last name must have at least 3 characters")
    private String lastName;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @NotBlank(message = "Professor email is mandatory")
    @Email(message = "Professor email must be a well-formed email address")
    private String email;

    @Min(value = 1000, message = "Professor salary must be greater than 1000")
    @Max(value = 100000, message = "Professor salary cannot exceed 100000")
    private float salary;

}
