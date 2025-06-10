package exceptions;

import java.io.IOException;

/**
 * Exception indicating an invalid resolution.
 */
public class OutOfBoundariesException extends IOException {
    private static final String OutOfBoundariesException = "Did not change resolution due to " +
            "exceeding boundaries.";

    /**
     * Constructs a new OutOfBoundariesException with the default error message.
     */
    public OutOfBoundariesException() {
        super(OutOfBoundariesException);
    }
}
