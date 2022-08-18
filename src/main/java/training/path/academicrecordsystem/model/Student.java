package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class Student extends User {

    @Min(value = 0, message = "Grades must be between 0.0 and 5.0")
    @Max(value = 5, message = "Grades must be between 0.0 and 5.0")
    private float averageGrade;

    @NotNull(message = "A student must be associated to a career")
    private Career career;

    public Student() {
    }

    @Builder
    public Student(String id, String name, String email, float averageGrade, Career career) {
        super(id, name, email);
        this.averageGrade = averageGrade;
        this.career = career;
    }
}
