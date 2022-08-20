package training.path.academicrecordsystem.controllers.dtos;

import lombok.Builder;
import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Min(value = 1, message = "A class must have at least a capacity of one student")
    @Max(value = 35, message = "A class can only have a maximum of 35 students")
    private int capacity;

    private boolean available;

    public RequestBodyCourseClassDTO() {
    }

    @Builder
    public RequestBodyCourseClassDTO(String id, String courseId, String professorId, int capacity, boolean available) {
        this.id = id;
        this.courseId = courseId;
        this.professorId = professorId;
        this.capacity = capacity;
        this.available = available;
    }
}
