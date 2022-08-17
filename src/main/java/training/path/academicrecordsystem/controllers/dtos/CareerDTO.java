package training.path.academicrecordsystem.controllers.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CareerDTO {

    private String id;
    @NotBlank(message = "You must provide the career name")
    private String name;

}
