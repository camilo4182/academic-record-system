package training.path.academicrecordsystem.exceptions;

public class EmptyUserNameException extends Exception {

    public EmptyUserNameException() {
        super("User name cannot be empty");
    }

}
