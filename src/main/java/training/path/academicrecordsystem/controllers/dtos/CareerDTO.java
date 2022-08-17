package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import lombok.NonNull;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
public class CareerDTO {

    @Null(message = "Invalid id. Don't provide id for career", groups = OnCreate.class)
    @NotBlank(message = "Id cannot be null", groups = OnUpdate.class)
    @Pattern(message = "Invalid id format", regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")
    private String id;
    @NotBlank(message = "You must provide the career name")
    private String name;

}
