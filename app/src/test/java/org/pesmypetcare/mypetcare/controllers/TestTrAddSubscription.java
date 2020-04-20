package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupNotExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrAddSubscription {
    private User user;
    private Group group;
    private TrAddSubscription trAddSubscription;

    @Before
    public void setUp() {
        user = new User("John Smith", "johnSmith@gmail.com", "1234");
        group = new Group("Husky", "John Doe", DateTime.Builder.buildDateString("2020-04-15"));
        trAddSubscription = new TrAddSubscription(new StubCommunityService());
    }

    @Test(expected = GroupNotExistingException.class)
    public void shouldNotAddSubscriptionToNonExistingGroup() throws GroupNotExistingException {
        trAddSubscription.setUser(user);
        trAddSubscription.setGroup(new Group("petsBCN", "John Doe", DateTime.Builder.buildDateString("2020-04-15")));
        trAddSubscription.execute();
    }

    @Test
    public void shouldAddSubscriptionToGroup() throws GroupNotExistingException {
        trAddSubscription.setUser(user);
        trAddSubscription.setGroup(group);
        trAddSubscription.execute();

        assertEquals("Should add subscription to group", "[John Doe, John Smith]", group.getSubscribers().toString());
    }
}
