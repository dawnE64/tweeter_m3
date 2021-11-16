package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AlreadyAuthenticatedTask {
    private static final String LOG_TAG = "IsFollowerTask";
    public static final String IS_FOLLOWER_KEY = "is-follower";
    static final String URL_PATH = "/isfollower";
    private boolean isFollower;

    private User targetUser;
    private User potentialFollower;

    public IsFollowerTask(Handler messageHandler, AuthToken authToken, User targetUser, User potentialFollower) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
        this.potentialFollower = potentialFollower;
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        IsFollowerRequest request = new IsFollowerRequest(authToken, targetUser, potentialFollower);
        IsFollowerResponse response = getServerFacade().isFollower(request, URL_PATH);
        isFollower = response.isFollower();
        return response.isSuccess();
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, this.isFollower);
    }
}
