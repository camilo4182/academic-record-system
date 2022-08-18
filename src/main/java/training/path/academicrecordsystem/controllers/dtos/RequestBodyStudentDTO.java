package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RequestBodyStudentDTO {

    private String id;
    private String name;
    private String email;

    @NotBlank(message = "A student must be associated to a career")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String careerId;

    private float averageGrade;

}
