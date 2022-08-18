package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RequestBodyCourseClassDTO {

    private String id;

    @NotBlank(message = "A class must be associated to a course")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String courseId;

    @NotBlank(message = "A class must have a professor associated to it")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String professorId;

    private int capacity;
    private int enrolledStudents;
    private boolean available;

}
