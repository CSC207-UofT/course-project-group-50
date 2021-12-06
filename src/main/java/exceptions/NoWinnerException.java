package exceptions;

public class NoWinnerException extends RuntimeException {

    private static final String MSG = "Cannot call this method when there is no current winner.";

    public NoWinnerException() {
        super(MSG);
    }

}
