package training.path.academicrecordsystem.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Student extends User {

    private float averageGrade;

    public Student() {
    }

}
