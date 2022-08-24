package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;
import training.path.academicrecordsystem.validations.groups.OnUpdateByStudent;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=true)
public class Student extends User {

    @Min(value = 0, message = "Grades must be between 0.0 and 5.0", groups = {OnCreate.class, OnUpdate.class})
    @Max(value = 5, message = "Grades must be between 0.0 and 5.0", groups = {OnCreate.class, OnUpdate.class})
    private float averageGrade;

    @NotNull(message = "A student must be associated to a career", groups = {OnCreate.class, OnUpdate.class})
    private Career career;

    public Student() {
    }

    @Builder
    public Student(String id, String firstName, String lastName, String userName, String password, String email, Role role, float averageGrade, Career career) {
        super(id, firstName, lastName, userName, password, email, role);
        this.averageGrade = averageGrade;
        this.career = career;
    }
}
