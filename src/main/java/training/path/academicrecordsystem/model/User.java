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

    @NotBlank(message = "User first name is mandatory", groups = {OnCreate.class, OnUpdateByStudent.class})
    @Size(min = 3, message = "User first name must have at least 3 characters", groups = {OnCreate.class, OnUpdateByStudent.class})
    private String firstName;

    @NotBlank(message = "User last name is mandatory", groups = {OnCreate.class, OnUpdateByStudent.class})
    @Size(min = 3, message = "User last name must have at least 3 characters", groups = {OnCreate.class, OnUpdateByStudent.class})
    private String lastName;

    @NotBlank(message = "Username is mandatory", groups = {OnCreate.class, OnUpdateByStudent.class})
    @Size(min = 6, message = "Username must have at least 6 characters", groups = {OnCreate.class, OnUpdateByStudent.class})
    private String userName;

    @NotBlank(message = "Password is mandatory", groups = {OnCreate.class, OnUpdateByStudent.class})
    @Size(min = 8, message = "Password must have at least 8 characters", groups = {OnCreate.class, OnUpdateByStudent.class})
    private String password;

    @NotBlank(message = "User email is mandatory", groups = {OnCreate.class, OnUpdateByStudent.class})
    @Email(message = "User email must be a well-formed email address", groups = {OnCreate.class, OnUpdateByStudent.class})
    private String email;

    private Role role;

    public User() {
    }

    public User(String id, String firstName, String lastName, String userName, String password, String email, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
