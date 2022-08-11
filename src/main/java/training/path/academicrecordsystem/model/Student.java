package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Student extends User {

    private float averageGrade;
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
