package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;

public class GetCountTask extends AlreadyAuthenticatedTask {

    private static final String LOG_TAG = "GetCountTask";
    public static final String FOLLOWER_COUNT_KEY = "count";
    public static final String FOLLOWING_COUNT_KEY = "count";
    static final String URL_PATH = "/getcount";

    /**
     * User whose count is being received. Could be using user, could be some other person.
     */
    protected User targetUser;

    /**
     * Count returned by the server, gotten by a child.runTask()
     */
    protected int followersCount;
    protected int followingCount;

    public GetCountTask(Handler messageHandler, AuthToken authToken, User targetUser) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        GetCountRequest request = new GetCountRequest(authToken, targetUser); // todo: This ignores all input... :\ I'm worried that stuff doesn't work.
        GetCountResponse response = getServerFacade().getCounts(request, URL_PATH);

        followersCount = response.getFollowersCount();
        followingCount = response.getFollowingCount();

        return response.isSuccess();
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putInt(FOLLOWER_COUNT_KEY, followersCount);
        msgBundle.putInt(FOLLOWING_COUNT_KEY, followingCount);
    }

}
