package edu.byu.cs.tweeter.client.model.net;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class ServerFacadeUnitTest {
    private ServerFacade serverFacade;
    User user1;
    User user2;
    List<User> user1TenFollowers;
    AuthToken fakeAuth = new AuthToken("");
    String password1;

    public ServerFacadeUnitTest() {
        user1 = getFakeData().getFirstUser();
        user2 = getFakeData().getFakeUsers().get(1);

        user1TenFollowers = getFakeData().getFakeUsers().stream().limit(11).collect(Collectors.toList());
        user1TenFollowers.remove(0); //remove first person whose followers we want to look at

        password1 = "passw";
    }

    private ServerFacade getServerFacade() {
        if( serverFacade == null ) {
            serverFacade = new ServerFacade();
        }
        return serverFacade;
    }

    private FakeData getFakeData() {
        return new FakeData();
    }

    @BeforeEach
    void setup() {
        serverFacade = getServerFacade();
    }

    @Test
    @DisplayName("ServerFacade should be able to register with server")
    void serverFacade_testRegister() throws IOException, TweeterRemoteException {
        RegisterRequest request = new RegisterRequest(user1.getFirstName(), user1.getLastName(),
                user1.getAlias(), password1, user1.getImageUrl());

        RegisterResponse response = getServerFacade().register(request, "/register");

        assertNotNull(response);
        assertEquals(response.getUser(), user1);
        assertNotEquals(response.getUser(), user2);
    }

    @Test
    @DisplayName("ServerFacade should be able to get Followers for a target from server")
    void serverFacade_testGetFollowers() throws IOException, TweeterRemoteException {
        FollowersRequest request = new FollowersRequest(fakeAuth, user1.getAlias(),
                10, user1.getAlias());
        FollowersResponse response = getServerFacade().getFollowers(request, "/getfollowers");

        assertNotNull(response);
        assertEquals(response.getFollowers(), user1TenFollowers);
    }

    @Test
    @DisplayName("ServerFacade should be able to get count of user's followers/Following")
    void serverFacade_testGetCounts() throws IOException, TweeterRemoteException {
        GetCountRequest request = new GetCountRequest(fakeAuth, user1);
        GetCountResponse response = getServerFacade().getCounts(request, "/getcount");

        assertNotNull(response);
        assertEquals(response.getFollowersCount(), 21);
        assertEquals(response.getFollowingCount(), 21);
    }

}
