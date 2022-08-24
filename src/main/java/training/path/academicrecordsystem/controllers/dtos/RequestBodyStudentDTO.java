package training.path.academicrecordsystem.controllers.dtos;

import lombok.Builder;
import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdateByStudent;

import javax.validation.constraints.*;

/**
 * DTO for student creation
 */

@Data
@Builder
public class RequestBodyStudentDTO {

    @Null(message = "Don't provide id for student", groups = OnCreate.class)
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

    @NotBlank(message = "Student email is mandatory")
    @Email(message = "Student email must be a well-formed email address")
    private String email;

    @NotBlank(message = "A student must be associated to a career")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String careerId;

}
