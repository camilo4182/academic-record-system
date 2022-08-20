package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.*;

@Data
public class Course {

    @NotBlank(message = "Course id cannot be empty")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "You must provide a course name")
    @Size(min = 4, message = "Course name must have at least 4 characters")
    private String name;

    @Min(value = 0, message = "A course can only have credits between 0 and 10")
    @Max(value = 10, message = "A course can only have credits between 0 and 10")
    private int credits;

    public Course() {
    }

    @Builder
    public Course(String id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

}
