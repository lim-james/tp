package seedu.job.logic.parser.exceptions;

import seedu.job.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException addDescriptor(String description) {
        String formattedMessage = String.format(this.getMessage(), description);
        return new ParseException(formattedMessage, this.getCause());
    }
}
