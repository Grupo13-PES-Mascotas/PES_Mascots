package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;

/**
 * @author Albert Pinto
 */
public interface InfoGroupCommunication {
    /**
     * Get the current user.
     * @return The current user
     */
    User getUser();

    /**
     * Set the toolbar title.
     * @param title The toolbar title
     */
    void setToolbar(String title);

    /**
     * Add subscription to group.
     * @param group The group to add a subscription to
     */
    void addSubscription(Group group);

    /**
     * Remove the current user from the group.
     * @param group The group to remove the user from
     */
    void removeSubscription(Group group);

    void deleteForum(Forum forum);
}
