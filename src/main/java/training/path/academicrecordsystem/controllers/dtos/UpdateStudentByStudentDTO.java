package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.*;

/**
 * DTO for student update by student
 */

@Data
public class UpdateStudentByStudentDTO {

    @Null(message = "Don't provide id for student")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "Student name is mandatory")
    @Size(min = 4, message = "Student name must have at least 4 characters")
    private String name;

    @NotBlank(message = "Student email is mandatory")
    @Email(message = "Student email must be a well-formed email address")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

}
