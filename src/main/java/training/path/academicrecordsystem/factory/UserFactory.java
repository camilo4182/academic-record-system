package training.path.academicrecordsystem.factory;

import training.path.academicrecordsystem.model.Administrator;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.model.Student;
import training.path.academicrecordsystem.model.User;

import static training.path.academicrecordsystem.security.IRoles.*;

public class UserFactory {

    public static User create(String rol) {
        return switch (rol) {
            case ADMIN -> new Administrator();
            case STUDENT -> new Student();
            case PROFESSOR -> new Professor();
            default -> null;
        };
    }

}
