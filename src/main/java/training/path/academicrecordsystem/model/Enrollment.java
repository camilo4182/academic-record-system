package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnEnrollClasses;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Enrollment {

    @NotBlank(message = "Enrollment id cannot be blank")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @Min(value = 1, message = "Semester must be between 1 and 12", groups = OnEnrollClasses.class)
    @Max(value = 12, message = "Semester must be between 1 and 12", groups = OnEnrollClasses.class)
    private int semester;

    @NotNull(message = "Enrollment must be associated to a student")
    private Student student;

    @NotEmpty(message = "Enrollments must have at least one class registered", groups = OnEnrollClasses.class)
    private Set<CourseClass> courseClasses;

    public Enrollment() {
        courseClasses = new HashSet<>();
    }

    @Builder
    public Enrollment(String id, int semester, Student student, Set<CourseClass> courseClasses) {
        this.id = id;
        this.semester = semester;
        this.student = student;
        this.courseClasses = courseClasses;
    }

    public void addClass(CourseClass courseClass) {
        courseClasses.add(courseClass);
    }

    public List<CourseClass> getCourseClasses() {
        return courseClasses.stream().toList();
    }
}
