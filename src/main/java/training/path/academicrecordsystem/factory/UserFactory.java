package training.path.academicrecordsystem.factory;

import training.path.academicrecordsystem.model.Administrator;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.model.User;

import java.util.Optional;

import static training.path.academicrecordsystem.security.interfaces.IRoles.*;

public class UserFactory {

    public static Optional<User> create(String rol) {
        return switch (rol) {
            case "ROLE_" + ADMIN -> Optional.of(new Administrator());
            case "ROLE_" + STUDENT -> Optional.of(new Student());
            case "ROLE_" + PROFESSOR -> Optional.of(new Professor());
            default -> Optional.empty();
        };
    }

}
