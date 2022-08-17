package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import training.path.academicrecordsystem.validations.groups.OnAssignToCareer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Career {

    @NotBlank(message = "Career id cannot be null")
    @Pattern(message = "Invalid id format", regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")
    private String id;

    @NotBlank(message = "Career name cannot be null or blank")
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
