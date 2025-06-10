package exceptions;

import java.io.IOException;

/**
 * Exception indicating an incorrect format of the wanted command.
 */
public class IncorrectFormatException extends IOException {
    private static final String INCORRECT_FORMAT = "Did not %s due to incorrect format.";

    /**
     * Constructs a new IncorrectFormatException based on the command type it represents.
     */
    public IncorrectFormatException(String command) {
        super(String.format(INCORRECT_FORMAT, command));
    }
}
