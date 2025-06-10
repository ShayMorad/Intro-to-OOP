package exceptions;

import java.io.IOException;

/**
 * Exception indicating an incorrect command.
 */
public class IncorrectCommandException extends IOException {
    private static final String INCORRECT_COMMAND = "Did not execute due to incorrect command.";

    /**
     * Constructs a new IncorrectCommandException with the default error message.
     */
    public IncorrectCommandException() {
        super(INCORRECT_COMMAND);
    }
}
