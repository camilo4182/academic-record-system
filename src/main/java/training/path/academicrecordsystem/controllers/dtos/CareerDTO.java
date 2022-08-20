package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import lombok.NonNull;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnAssignToCareer;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;

import javax.validation.constraints.*;

@Data
public class CareerDTO {

    @Null(message = "Don't provide id for career")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "You must provide the career name")
    @Size(min = 4, message = "Career name must have at least 4 characters")
    private String name;

}
