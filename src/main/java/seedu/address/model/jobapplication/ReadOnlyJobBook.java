package seedu.address.model.jobapplication;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyJobBook {

    /**
     * Returns an unmodifiable view of the JobApplication list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<JobApplication> getApplicationList();

}
