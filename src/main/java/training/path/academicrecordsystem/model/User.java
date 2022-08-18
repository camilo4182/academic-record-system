package training.path.academicrecordsystem.model;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public abstract class User {

    @NotBlank(message = "User id cannot be blank")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "User name cannot be blank")
    private String name;

    @NotBlank(message = "User email cannot be blank")
    @Email(message = "User email must be a well-formed email address")
    private String email;

    public User() {
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
