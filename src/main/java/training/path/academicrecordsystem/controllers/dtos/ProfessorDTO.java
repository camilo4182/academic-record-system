package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class ProfessorDTO {

    private String id;
    private String name;
    private String email;
    private float salary;

    public ProfessorDTO() {
    }

    public ProfessorDTO(String id, String name, String email, float salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
    }
}
