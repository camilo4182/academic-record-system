package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.*;

/**
 * DTO for student update by student
 */

@Data
public class UpdateUserByUserDTO {

    @Null(message = "Don't provide id for student")
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


}
