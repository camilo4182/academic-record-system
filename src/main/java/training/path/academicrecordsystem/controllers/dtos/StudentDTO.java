package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class StudentDTO {

    private String id;
    private String name;
    private String email;
    private float averageGrade;

    public StudentDTO() {
    }
}
