package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedTask<User> {
    private static final String LOG_TAG = "GetFollowingTask";
    static final String URL_PATH = "/getfollowing";

    public GetFollowingTask(Handler messageHandler, AuthToken authToken, User targetUser, int limit, User lastItem) {
        super(messageHandler, authToken, targetUser, limit, lastItem);
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        String lastItemAlias = "";
        if (lastItem != null) { lastItemAlias = lastItem.getAlias(); }

        FollowingRequest request = new FollowingRequest(authToken, targetUser.getAlias(), limit, lastItemAlias);
        FollowingResponse response = getServerFacade().getFollowees(request, URL_PATH);

        if (response.isSuccess()) {
            items = response.getFollowees();
            hasMorePages = response.getHasMorePages();
            loadImagesForUsers(response.getFollowees());
        }

        return response.isSuccess();
    }
}