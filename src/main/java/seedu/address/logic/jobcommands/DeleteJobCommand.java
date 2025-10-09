package seedu.address.logic.jobcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.JobMessages;
import seedu.address.logic.jobcommands.exceptions.JobCommandException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.Model;

/**
 * Represents a command that deletes a job identified by the index number of the job in the list.
 */

public class DeleteJobCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the Application identified by the index number used in the displayed Application list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_APPLICATION_SUCCESS = "Deleted Application: %1$s";

    protected final Index targetIndex;

    public DeleteJobCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws JobCommandException {
        requireNonNull(model);
        List<JobApplication> lastShownList = model.getFilteredApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new JobCommandException(JobMessages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        JobApplication jobToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteJobApplication(jobToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_APPLICATION_SUCCESS, JobMessages.format(jobToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteJobCommand)) {
            return false;
        }

        DeleteJobCommand otherDeleteCommand = (DeleteJobCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .toString();
    }
}

