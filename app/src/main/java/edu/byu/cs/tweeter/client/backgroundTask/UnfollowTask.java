package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends AlreadyAuthenticatedTask {
    private static final String LOG_TAG = "UnfollowTask";
    static final String URL_PATH = "/unfollow";

    /**
     * The user used in runTask() to un-follow
     */
    private User toUnfollow;

    public UnfollowTask(Handler messageHandler, AuthToken authToken, User targetUser) {
        super(messageHandler, authToken);
        this.toUnfollow = targetUser;
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        UnfollowRequest request = new UnfollowRequest(authToken, toUnfollow);
        UnfollowResponse response = getServerFacade().unfollow(request, URL_PATH);

        return response.isSuccess();
    }
}
