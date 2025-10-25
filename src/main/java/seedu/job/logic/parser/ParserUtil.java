package seedu.job.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.job.logic.JobMessages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.job.commons.core.index.Index;
import seedu.job.commons.util.StringUtil;
import seedu.job.logic.parser.exceptions.ParseException;
import seedu.job.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses the given argument string into an array of raw tokens separated by whitespace.
     * Leading and trailing spaces are ignored.
     *
     * @param args the input string to tokenize
     * @return an array of tokens split by one or more whitespace characters
     * @throws ParseException if the input string is empty or contains only whitespace
     */
    public static String[] parseRawTokens(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return trimmed.split("\\s+");
    }


    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Validates that the given {@code deadline} is not in the past.
     *
     * @param deadline The deadline to validate.
     * @throws ParseException if the deadline is in the past.
     */
    public static void validateDeadlineNotInPast(LocalDateTime deadline) throws ParseException {
        requireNonNull(deadline);
        if (deadline.isBefore(LocalDateTime.now())) {
            throw new ParseException("Deadline cannot be in the past. Please provide a future date and time.");
        }
    }
}
