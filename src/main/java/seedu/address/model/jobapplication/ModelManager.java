package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final JobBook jobBook;
    private final UserPrefs userPrefs;
    private final FilteredList<JobApplication> filteredApplications;

    /**
     * Initializes a ModelManager with the given JobBook and userPrefs.
     */
    public ModelManager(ReadOnlyJobBook jobBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(jobBook, userPrefs);

        logger.fine("Initializing with address book: " + jobBook + " and user prefs " + userPrefs);

        this.jobBook = new JobBook(new JobBook());
        this.userPrefs = new UserPrefs(userPrefs);
        filteredApplications = new FilteredList<>(this.jobBook.getApplicationList());
    }

    public ModelManager() {
        this(new JobBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getJobBookFilePath() {
        return userPrefs.getJobBookFilePath();
    }

    @Override
    public void setJobBookFilePath(Path jobBookFilePath) {
        requireNonNull(jobBookFilePath);
        userPrefs.setJobBookFilePath(jobBookFilePath);
    }

    //=========== JobBook ================================================================================

    @Override
    public void setJobBook(ReadOnlyJobBook jobBook) {
        this.jobBook.resetData(jobBook);
    }

    @Override
    public ReadOnlyJobBook getJobBook() {
        return jobBook;
    }

    @Override
    public boolean hasApplication(JobApplication application) {
        requireNonNull(application);
        return jobBook.hasJobApplication(application);
    }

    @Override
    public void deleteJobApplication(JobApplication target) {
        jobBook.removeApplication(target);
    }

    @Override
    public void addJobApplication(JobApplication person) {
        jobBook.addApplication(person);
        updateFilteredJobApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);
    }

    @Override
    public void setJobApplication(JobApplication target, JobApplication editedApplication) {
        requireAllNonNull(target, editedApplication);

        jobBook.setApplication(target, editedApplication);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedJobBook}
     */
    @Override
    public ObservableList<JobApplication> getFilteredApplicationList() {
        return filteredApplications;
    }

    @Override
    public void updateFilteredJobApplicationList(Predicate<JobApplication> predicate) {
        requireNonNull(predicate);
        filteredApplications.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return jobBook.equals(otherModelManager.jobBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredApplications.equals(otherModelManager.filteredApplications);
    }

}
