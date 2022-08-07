package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
public class Professor extends User {

    private float salary;
    private Set<CourseClass> classes;

    public Professor() {
    }

    @Builder
    public Professor(String id, String name, String email, float salary, Set<CourseClass> classes) {
        super(id, name, email);
        this.salary = salary;
        this.classes = classes;
    }
}
