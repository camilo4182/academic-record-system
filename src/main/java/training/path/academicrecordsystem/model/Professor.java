package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@EqualsAndHashCode(callSuper=true)
public class Professor extends User {

    @Min(value = 1000, message = "Professor salary must be greater than 1000")
    @Max(value = 100000, message = "Professor salary cannot exceed 100000")
    private float salary;

    public Professor() {
    }

    @Builder
    public Professor(String id, String name, String email, float salary) {
        super(id, name, email);
        this.salary = salary;
    }

}
