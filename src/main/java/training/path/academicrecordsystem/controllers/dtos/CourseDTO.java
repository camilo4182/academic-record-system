package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CourseDTO {

    private String id;
    @NotBlank(message = "You must provide the course name")
    private String name;
    private int credits;

}
