package seedu.job.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.job.logic.JobMessages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.job.logic.jobcommands.FindCommand;
import seedu.job.logic.parser.exceptions.ParseException;
import seedu.job.model.jobapplication.NameContainsKeywordsPredicate;

/**
 * Unit tests for {@link FindCommandParser}.
 *
 * Domain context: job applications (keywords like "developer", "intern", "marketing").
 *
 * Coverage:
 * 1. Valid parsing of single / multiple / spaced keywords
 * 2. Whitespace handling
 * 3. Exception correctness for empty and whitespace-only inputs
 * 4. Predicate integrity (keyword list equality)
 */
public class FindCommandParserTest {

    private FindCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new FindCommandParser();
    }

    @Test
    public void parse_validSingleKeyword_returnsFindCommand() throws Exception {
        String input = "developer";
        List<String> expectedKeywords = List.of("developer");
        FindCommand expectedCommand =
                new FindCommand(new NameContainsKeywordsPredicate(expectedKeywords));

        FindCommand result = parser.parse(input);
        assertEquals(expectedCommand, result,
                "Expected FindCommand to contain predicate with keyword [developer]");
    }

    @Test
    public void parse_validMultipleKeywords_returnsFindCommand() throws Exception {
        String input = "developer intern marketing";
        List<String> expectedKeywords = Arrays.asList("developer", "intern", "marketing");
        FindCommand expectedCommand =
                new FindCommand(new NameContainsKeywordsPredicate(expectedKeywords));

        FindCommand result = parser.parse(input);
        assertEquals(expectedCommand, result,
                "Expected FindCommand to contain predicate with keywords [developer, intern, marketing]");
    }

    @Test
    public void parse_extraWhitespace_trimsAndSplitsCorrectly() throws Exception {
        String input = "   data   analyst   ";
        List<String> expectedKeywords = Arrays.asList("data", "analyst");
        FindCommand expectedCommand =
                new FindCommand(new NameContainsKeywordsPredicate(expectedKeywords));

        FindCommand result = parser.parse(input);
        assertEquals(expectedCommand, result,
                "Parser should trim and split whitespace properly for job-related keywords");
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String input = "";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(input));
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertEquals(expectedMessage, thrown.getMessage(),
                "Expected parse() to throw ParseException with correct format message");
    }

    @Test
    public void parse_whitespaceOnlyInput_throwsParseException() {
        String input = "     ";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(input));
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertEquals(expectedMessage, thrown.getMessage(),
                "Expected parse() to throw ParseException with correct format message");
    }
}

