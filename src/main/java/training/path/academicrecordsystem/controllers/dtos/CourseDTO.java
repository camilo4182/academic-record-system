package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnCreate;

import javax.validation.constraints.*;

@Data
public class CourseDTO {

    @Null(message = "Don't provide id for course", groups = OnCreate.class)
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "You must provide the course name")
    @Size(min = 4, message = "Course name must have at least 4 characters")
    private String name;

    @Min(value = 0, message = "A course can only have credits between 0 and 10")
    @Max(value = 10, message = "A course can only have credits between 0 and 10")
    private int credits;

}
