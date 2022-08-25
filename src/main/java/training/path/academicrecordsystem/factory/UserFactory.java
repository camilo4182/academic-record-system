package training.path.academicrecordsystem.factory;

import training.path.academicrecordsystem.model.Administrator;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.model.User;

import static training.path.academicrecordsystem.security.interfaces.IRoles.*;

public class UserFactory {

    public static User create(String rol) {
        return switch (rol) {
            case "ROLE_" + ADMIN -> new Administrator();
            case "ROLE_" + STUDENT -> new Student();
            case "ROLE_" + PROFESSOR -> new Professor();
            default -> null;
        };
    }

}
