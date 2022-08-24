package training.path.academicrecordsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @NotBlank(message = "Id cannot be blank")
    private String id;

    @NotBlank(message = "Role cannot be blank")
    private String role;

}
