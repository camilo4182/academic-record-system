package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnAssignToCareer;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Career {

    @NotBlank(message = "Career id cannot be null")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "You must provide the career name")
    @Size(min = 4, message = "Career name must have at least 4 characters")
    private String name;

    private Set<Course> courses;

    public Career() {
        courses = new HashSet<>();
    }

    @Builder
    public Career(String id, String name, Set<Course> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public List<Course> getCourses() {
        return courses.stream().toList();
    }
}
