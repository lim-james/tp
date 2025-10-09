package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.jobapplication.JobApplication;


/**
 * Container for user visible messages.
 */
public class JobMessages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX =
        "The application index provided is invalid";
    public static final String MESSAGE_APPLICATIONS_LISTED_OVERVIEW = "%1$d applications listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code Application} for display to the user.
     */
    public static String format(JobApplication application) {
        final StringBuilder builder = new StringBuilder();
        builder.append(application.getCompanyName())
                .append("; Role: ")
                .append(application.getRole())
                .append("; Status: ")
                .append(application.getStatus())
                .append("; Deadline: ")
                .append(application.getDeadline());
        return builder.toString();
    }

}
