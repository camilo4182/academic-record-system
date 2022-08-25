package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Role {

    @NotBlank(message = "Id cannot be blank")
    private String id;

    @NotBlank(message = "Role cannot be blank")
    private String roleName;

    public Role() {
    }

    @Builder
    public Role(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
