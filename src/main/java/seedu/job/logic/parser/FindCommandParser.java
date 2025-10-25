package seedu.job.logic.parser;

import java.util.Arrays;

import seedu.job.logic.jobcommands.FindCommand;
import seedu.job.logic.parser.exceptions.ParseException;
import seedu.job.model.jobapplication.NameContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements JobParser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String[] nameKeywords;
        try {
            nameKeywords = ParserUtil.parseRawTokens(args);
        } catch (ParseException ex) {
            throw ex.addDescriptor(FindCommand.MESSAGE_USAGE);
        }
        
        var predicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        return new FindCommand(predicate);
    }

}
