package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

@Data
public class ResponseBodyProfessorDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;

}
