package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;
import training.path.academicrecordsystem.validations.groups.OnAssignToCareer;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;

import javax.validation.constraints.*;

@Data
public class CourseDTO {

    @Null(message = "Don't provide id for career", groups = OnCreate.class)
    @NotBlank(message = "Course id cannot be null", groups = OnUpdate.class)
    @Pattern(message = "Invalid id format", regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", groups = {OnUpdate.class, OnAssignToCareer.class})
    private String id;

    @Null(groups = OnAssignToCareer.class)
    @NotBlank(message = "You must provide the course name", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Min(0)
    @Max(10)
    private int credits;

}
