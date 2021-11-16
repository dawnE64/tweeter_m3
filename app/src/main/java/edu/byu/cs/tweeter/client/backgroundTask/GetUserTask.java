package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AlreadyAuthenticatedTask {
    private static final String LOG_TAG = "GetUserTask";
    public static final String USER_KEY = "user";
    static final String URL_PATH = "/getuser";
    private String alias; // used to get the correct user
    private User user; // user being got and returned up

    public GetUserTask(Handler messageHandler, AuthToken authToken, String alias) {
        super(messageHandler, authToken);
        this.alias = alias;
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        GetUserRequest request = new GetUserRequest(authToken, alias);
        GetUserResponse response = getServerFacade().getUser(request, URL_PATH);
        user = response.getUser();
        loadImage(user);
        return response.isSuccess();
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, user);
    }
}
