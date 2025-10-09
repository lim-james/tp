package seedu.address.model.jobapplication;
import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameApplication comparison)
 */
public class JobBook implements ReadOnlyJobBook {

    private final UniqueApplicationList applications;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        applications = new UniqueApplicationList();
    }

    public JobBook() {}

    /**
     * Creates an AddressBook using the JobApplications in the {@code toBeCopied}
     */
    public JobBook(ReadOnlyJobBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the JobApplication list with {@code JobApplications}.
     * {@code JobApplications} must not contain duplicate JobApplications.
     */
    public void setApplications(List<JobApplication> applications) {
        this.applications.setJobApplications(applications);
    }

    /**
     * Resets the existing data of this {@code JobBook} with {@code newData}.
     */
    public void resetData(ReadOnlyJobBook newData) {
        requireNonNull(newData);

        setApplications(newData.getApplicationList());
    }

    //// JobApplication-level operations

    /**
     * Returns true if a JobApplication with the same identity as {@code JobApplication} exists in the Job book.
     */
    public boolean hasJobApplication(JobApplication application) {
        requireNonNull(application);
        return applications.contains(application);
    }

    /**
     * Adds a JobApplication to the Job book.
     * The JobApplication must not already exist in the Job book.
     */
    public void addApplication(JobApplication p) {
        applications.add(p);
    }

    /**
     * Replaces the given JobApplication {@code target} in the list with {@code editedJobApplication}.
     * {@code target} must exist in the Job book.
     * The JobApplication identity of {@code editedJobApplication} must not be the same as another existing
     * JobApplication in the Job book.
     */
    public void setApplication(JobApplication target, JobApplication editedJobApplication) {
        requireNonNull(editedJobApplication);

        applications.setJobApplication(target, editedJobApplication);
    }

    /**
     * Removes {@code key} from this {@code JobBook}.
     * {@code key} must exist in the Job book.
     */
    public void removeApplication(JobApplication key) {
        applications.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("JobApplications", applications)
                .toString();
    }

    @Override
    public ObservableList<JobApplication> getApplicationList() {
        return applications.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobBook)) {
            return false;
        }

        JobBook otherJobBook = (JobBook) other;
        return applications.equals(otherJobBook.applications);
    }

    @Override
    public int hashCode() {
        return applications.hashCode();
    }
}
