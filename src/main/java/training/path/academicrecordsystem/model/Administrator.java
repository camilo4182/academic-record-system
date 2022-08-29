package training.path.academicrecordsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
public class Administrator extends User {

    public Administrator() {
    }

    public Administrator(String id, String firstName, String lastName, String userName, String password, String email, Role role) {
        super(id, firstName, lastName, userName, password, email, role);
    }
}
