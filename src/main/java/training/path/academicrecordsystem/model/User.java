package training.path.academicrecordsystem.model;

import lombok.Data;
import training.path.academicrecordsystem.config.UUIDRegex;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;
import training.path.academicrecordsystem.validations.groups.OnUpdateByStudent;

import javax.validation.constraints.*;

@Data
public abstract class User {

    @NotBlank(message = "User id cannot be blank", groups = {OnCreate.class, OnUpdate.class, OnUpdateByStudent.class})
    @Pattern(regexp = UUIDRegex.UUIRegex, message = "Invalid id format", groups = {OnCreate.class, OnUpdate.class, OnUpdateByStudent.class})
    private String id;

    @NotBlank(message = "User name is mandatory", groups = {OnCreate.class, OnUpdateByStudent.class})
    @Size(min = 4, message = "User name must have at least 4 characters", groups = {OnCreate.class, OnUpdateByStudent.class})
    private String name;

    @NotBlank(message = "Password is mandatory", groups = {OnCreate.class, OnUpdateByStudent.class})
    @Size(min = 8, message = "Password must have at least 8 characters", groups = {OnCreate.class, OnUpdateByStudent.class})
    private String password;

    @NotBlank(message = "User email is mandatory", groups = {OnCreate.class, OnUpdateByStudent.class})
    @Email(message = "User email must be a well-formed email address", groups = {OnCreate.class, OnUpdateByStudent.class})
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
