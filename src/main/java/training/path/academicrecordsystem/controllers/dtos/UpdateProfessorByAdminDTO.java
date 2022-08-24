package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
public class UpdateProfessorByAdminDTO {

    @Null(message = "Don't provide id for student", groups = OnCreate.class)
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @Min(value = 1000, message = "Professor salary must be greater than 1000")
    @Max(value = 100000, message = "Professor salary cannot exceed 100000")
    private float salary;

}
