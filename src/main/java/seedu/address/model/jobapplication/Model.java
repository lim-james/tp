package seedu.address.model.jobapplication;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<JobApplication> PREDICATE_SHOW_ALL_APPLICATIONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getJobBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setJobBookFilePath(Path jobBookFilePath);

    /**
     * Replaces address book data with the data in {@code JobBook}.
     */
    void setJobBook(ReadOnlyJobBook jobBook);

    /** Returns the JobBook */
    ReadOnlyJobBook getJobBook();

    /**
     * Returns true if a application with the same identity as {@code JobApplication} exists in the address book.
     */
    boolean hasApplication(JobApplication application);

    /**
     * Deletes the given JobApplication.
     * The JobApplication must exist in the address book.
     */
    void deleteJobApplication(JobApplication target);

    /**
     * Adds the given JobApplication.
     * {@code JobApplication} must not already exist in the address book.
     */
    void addJobApplication(JobApplication application);

    /**
     * Replaces the given JobApplication {@code target} with {@code editedJobApplication}.
     * {@code target} must exist in the address book.
     * The JobApplication identity of {@code editedJobApplication} must not be the same as another existing
     * JobApplication in the address book.
     */
    void setJobApplication(JobApplication target, JobApplication editedJobApplication);

    /**
     * Returns an unmodifiable view of the filtered JobApplication list
     */
    ObservableList<JobApplication> getFilteredApplicationList();

    /**
     * Updates the filter of the filtered JobApplication list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredJobApplicationList(Predicate<JobApplication> predicate);
}
