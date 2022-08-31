package training.path.academicrecordsystem.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDTO {

    @NotBlank(message = "Please fill out this field")
    private String username;

    @NotBlank(message = "Please fill out this field")
    private String password;

}
