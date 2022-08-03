package training.path.academicrecordsystem.exceptions;

public class AlreadyExistingUserNameException extends Exception {

    public AlreadyExistingUserNameException(String name) {
        super("User name " + name + " is already taken");
    }
}
