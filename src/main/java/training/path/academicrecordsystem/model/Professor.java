package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
public class Professor extends User {

    @Min(value = 1000, message = "Professor salary must be greater than 1000", groups = {OnCreate.class, OnUpdate.class})
    @Max(value = 100000, message = "Professor salary cannot exceed 100000", groups = {OnCreate.class, OnUpdate.class})
    private float salary;

    private Set<CourseClass> classes;

    public Professor() {
    }

    @Builder
    public Professor(String id, String firstName, String lastName, String userName, String password, String email, Role role, float salary) {
        super(id, firstName, lastName, userName, password, email, role);
        this.salary = salary;
    }

    public void addClass(CourseClass courseClass) {
        classes.add(courseClass);
    }

    public List<CourseClass> getCourseClass() {
        return classes.stream().toList();
    }

}
