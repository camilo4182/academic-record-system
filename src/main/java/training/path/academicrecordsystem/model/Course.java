package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnAssignToCareer;
import training.path.academicrecordsystem.validations.groups.OnUpdate;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Course {

    @NotBlank(message = "Course id cannot be empty")
    @Pattern(message = "Invalid id format", regexp = UUIDRegex.UUIRegex)
    private String id;

    @NotBlank(message = "You must provide a course name")
    @Size(min = 4, message = "Course name must have at least 4 characters")
    private String name;

    @Min(value = 0, message = "A course must have a minimum of 0 credits")
    @Max(value = 10, message = "A course cannot have more than 10 credits")
    private int credits;

    private Set<Career> careers;
    private Map<String, CourseClass> classes;

    public Course() {
        careers = new HashSet<>();
    }

    @Builder
    public Course(String id, String name, int credits, Set<Career> careers, Map<String, CourseClass> classes) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.careers = careers;
        this.classes = classes;
    }

    public void addCareer(Career career) {
        careers.add(career);
    }

    public void removeCareer(Career career) {
        careers.remove(career);
    }

    public void addClass(CourseClass courseClass) {
        classes.put(courseClass.getId(), courseClass);
    }

    public void removeClass(CourseClass courseClass) {
        classes.remove(courseClass.getId());
    }
}
