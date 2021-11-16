package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.util.Random;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AlreadyAuthenticatedTask {
    private static final String LOG_TAG = "IsFollowerTask";
    public static final String IS_FOLLOWER_KEY = "is-follower";
    private boolean isFollower;

    private User targetUser;
    private User potentialFollower;

    public IsFollowerTask(Handler messageHandler, AuthToken authToken, User targetUser, User potentialFollower) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
        this.potentialFollower = potentialFollower;
    }

    @Override
    protected boolean runTask() {
        isFollower = (new Random().nextInt() > 0); // will need to actually work with server
        return true;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, this.isFollower);
    }
}
