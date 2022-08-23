package training.path.academicrecordsystem.model;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public abstract class User {

    @NotBlank(message = "User id cannot be blank")
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format")
    private String id;

    @NotBlank(message = "User name is mandatory")
    @Size(min = 4, message = "User name must have at least 4 characters")
    private String name;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @NotBlank(message = "User email is mandatory")
    @Email(message = "User email must be a well-formed email address")
    private String email;

    private String role;

    public User() {
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
