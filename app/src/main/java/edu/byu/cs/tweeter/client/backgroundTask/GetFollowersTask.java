package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedTask<User> {
    private static final String LOG_TAG = "GetFollowersTask";
    static final String URL_PATH = "/getfollowers";

    public GetFollowersTask(Handler messageHandler, AuthToken authToken, User targetUser, int limit, User lastItem) {
        super(messageHandler, authToken, targetUser, limit, lastItem);
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        String lastItemAlias = "";
        if (lastItem != null) { lastItemAlias = lastItem.getAlias(); }

        FollowersRequest request = new FollowersRequest(authToken, targetUser.getAlias(), limit, lastItemAlias);
        FollowersResponse response = getServerFacade().getFollowers(request, URL_PATH);

        if (response.isSuccess()) {
            items = response.getFollowers();
            hasMorePages = response.getHasMorePages();
            loadImagesForUsers(response.getFollowers());
        }

        return response.isSuccess();
    }

}
