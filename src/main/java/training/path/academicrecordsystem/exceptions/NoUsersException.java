package training.path.academicrecordsystem.exceptions;

public class NoUsersException extends Exception {

    public NoUsersException() {
        super("There are no registered users yet");
    }
}
