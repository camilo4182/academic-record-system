package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CourseOnlyIdDTO {

    @NotBlank(message = "You must provide the course id to assign")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String courseId;

}
