package exceptions;

import java.io.IOException;

/**
 * Exception indicating that the character set used for matching is empty.
 */
public class EmptyCharsetException extends IOException {

    private static final String EMPTY_CHARSET_ERROR = "Did not execute. Charset is empty.";

    /**
     * Constructs a new EmptyCharsetException with the default error message.
     */
    public EmptyCharsetException() {
        super(EMPTY_CHARSET_ERROR);
    }

}
