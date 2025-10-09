package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * A list of Applications that enforces uniqueness between its elements and does not allow nulls.
 * An Application is considered unique by comparing using {@code Application#isSameApplication(Application)}. As such,
 * adding and updating of Applications uses Application#isSameApplication(Application) for equality so as to ensure that
 * the Application being added or updated is unique in terms of identity in the UniqueApplicationList. However, the
 * removal of an Application uses Application#equals(Object) so as to ensure that the Application with exactly the same
 * fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see JobApplication#isSameJobApplication(JobApplication)
 */
public class UniqueApplicationList implements Iterable<JobApplication> {

    private final ObservableList<JobApplication> internalList = FXCollections.observableArrayList();
    private final ObservableList<JobApplication> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Application as the given argument.
     */
    public boolean contains(JobApplication toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameJobApplication);
    }

    /**
     * Adds a Application to the list.
     * The Application must not already exist in the list.
     */
    public void add(JobApplication toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Application {@code target} in the list with {@code editedApplication}.
     * {@code target} must exist in the list.
     * The Application identity of {@code editedApplication} must not be the same as another existing Application in the
     * list.
     */
    public void setJobApplication(JobApplication target, JobApplication editedApplication) {
        requireAllNonNull(target, editedApplication);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameJobApplication(editedApplication) && contains(editedApplication)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedApplication);
    }

    /**
     * Removes the equivalent Application from the list.
     * The Application must exist in the list.
     */
    public void remove(JobApplication toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setJobApplications(UniqueApplicationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Applications}.
     * {@code Applications} must not contain duplicate Applications.
     */
    public void setJobApplications(List<JobApplication> applications) {
        requireAllNonNull(applications);
        if (!applicationsAreUnique(applications)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(applications);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<JobApplication> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<JobApplication> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueApplicationList)) {
            return false;
        }

        UniqueApplicationList otherUniqueApplicationList = (UniqueApplicationList) other;
        return internalList.equals(otherUniqueApplicationList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code Applications} contains only unique Applications.
     */
    private boolean applicationsAreUnique(List<JobApplication> applications) {
        for (int i = 0; i < applications.size() - 1; i++) {
            for (int j = i + 1; j < applications.size(); j++) {
                if (applications.get(i).isSameJobApplication(applications.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
