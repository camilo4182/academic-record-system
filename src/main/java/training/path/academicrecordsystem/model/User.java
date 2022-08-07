package training.path.academicrecordsystem.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class User {

    private String id;
    private String name;
    private String email;

    public User() {
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
