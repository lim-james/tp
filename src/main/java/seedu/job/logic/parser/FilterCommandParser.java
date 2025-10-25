package seedu.job.logic.parser;

import static seedu.job.logic.JobMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.job.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.job.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.job.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.job.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.job.model.jobapplication.Model.PREDICATE_SHOW_ALL_APPLICATIONS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.job.logic.jobcommands.FilterCommand;
import seedu.job.logic.parser.exceptions.ParseException;
import seedu.job.model.jobapplication.DeadlinePredicate;
import seedu.job.model.jobapplication.JobApplication;
import seedu.job.model.jobapplication.RoleContainsKeywordPredicate;
import seedu.job.model.jobapplication.StatusMatchesKeywordPredicate;
import seedu.job.model.jobapplication.TagsContainKeywordPredicate;


/**
 * Represents an object that parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements JobParser<FilterCommand> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private static final Map<Prefix, Function<String, Predicate<JobApplication>>> PREDICATE_MAP = Map.of(
        PREFIX_TAG, FilterCommandParser::getTagPredicate,
        PREFIX_ROLE, FilterCommandParser::getRolePredicate,
        PREFIX_STATUS, FilterCommandParser::getStatusPredicate,
        PREFIX_DEADLINE, FilterCommandParser::getDeadlinePredicate
    );

    /**
     * Parses and constructs a {@link Predicate<JobApplication>} based on the first valid prefix
     * found in the given {@link ArgumentMultimap}. Supported prefixes include tag, role,
     * status, and deadline.
     *
     * <p>The corresponding predicate is determined by mapping the prefix to its builder function
     * defined in {@link #PREDICATE_MAP}. Each builder function transforms the provided string
     * argument into a {@code Predicate<JobApplication>} instance.
     *
     * @param argMultimap the parsed argument map containing possible filter prefixes
     * @return a predicate for filtering {@link JobApplication} objects
     * @throws ParseException if no valid prefix is found or if an argument is malformed
     */
    public static Predicate<JobApplication> getPredicate(ArgumentMultimap argMultimap) throws ParseException {
        for (var entry : PREDICATE_MAP.entrySet()) {
            Prefix prefix = entry.getKey();
            Optional<String> value = argMultimap.getValue(prefix);
            if (value.isPresent()) {
                argMultimap.verifyNoDuplicatePrefixesFor(prefix);
                return entry.getValue().apply(value.get().trim());
            }
        }
        throw new ParseException("No valid filter prefix provided.");
    }

    /** 
     * Builds a predicate that tests whether a job application contains a tag
     * matching the specified keyword (case-insensitive).
     *
     * @param keyword the tag keyword
     * @return predicate filtering by tag
     */
    private static Predicate<JobApplication> getTagPredicate(String keyword) {
        return new TagsContainKeywordPredicate(keyword.toLowerCase());
    }

    /**
     * Builds a predicate that tests whether a job application's role contains
     * the specified keyword (case-insensitive).
     *
     * @param keyword the role keyword
     * @return predicate filtering by role
     */
    private static Predicate<JobApplication> getRolePredicate(String keyword) {
        return new RoleContainsKeywordPredicate(keyword.toLowerCase());
    }

    /**
     * Builds a predicate that tests whether a job application's status matches
     * the specified keyword (case-insensitive).
     *
     * @param keyword the status keyword
     * @return predicate filtering by status
     */
    private static Predicate<JobApplication> getStatusPredicate(String keyword) {
        return new StatusPredicate(keyword.toLowerCase());
    }

    /**
     * Builds a predicate that tests whether a job application's deadline
     * matches the specified date.
     *
     * @param dateStr the deadline date in ISO format (yyyy-MM-dd)
     * @return predicate filtering by deadline
     * @throws ParseException if the date format is invalid
     */
    private static Predicate<JobApplication> getDeadlinePredicate(String dateStr) throws ParseException {
        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            return new DeadlinePredicate(date);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date format. Expected yyyy-MM-dd", e);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
        ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_ROLE, PREFIX_STATUS, PREFIX_DEADLINE);

        String preamble = argMultimap.getPreamble().trim();

        // Check if user wants to remove the existing filter
        if (preamble.equalsIgnoreCase("none")) {
            return new FilterCommand(PREDICATE_SHOW_ALL_APPLICATIONS);
        }

        if (!preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Prediate<JobApplication> predicate = getPredicate(argMultimap);
        return new FilterCommand(predicate);


    }
}
