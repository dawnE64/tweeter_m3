package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends AlreadyAuthenticatedTask {
    private static final String LOG_TAG = "FollowTask";
    static final String URL_PATH = "/follow";

    /**
     * The user used in runTask() to follow
     */
    private User toFollow;

    public FollowTask(Handler messageHandler, AuthToken authToken, User targetUser) {
        super(messageHandler, authToken);
        this.toFollow = targetUser;
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        FollowRequest request = new FollowRequest(authToken, toFollow);
        FollowResponse response = getServerFacade().follow(request, URL_PATH);

        return response.isSuccess();
    }
}
