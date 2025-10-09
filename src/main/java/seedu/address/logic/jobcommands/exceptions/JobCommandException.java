package seedu.address.logic.jobcommands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link jobCommand}.
 */
public class JobCommandException extends Exception {
    public JobCommandException(String message) {
        super(message);
    }
    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public JobCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
