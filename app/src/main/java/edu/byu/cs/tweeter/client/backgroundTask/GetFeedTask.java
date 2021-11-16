package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedTask<Status> {
    private static final String LOG_TAG = "GetFeedTask";
    static final String URL_PATH = "/getfeed";

    public GetFeedTask(Handler messageHandler, AuthToken authToken, User targetUser, int limit, Status lastItem) {
        super(messageHandler, authToken, targetUser, limit, lastItem);
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        GetFeedRequest request = new GetFeedRequest(authToken, targetUser, limit, lastItem);
        GetFeedResponse response = getServerFacade().getFeed(request, URL_PATH);

        if (response.isSuccess()) {
            items = response.getFeed();
            hasMorePages = response.getHasMorePages();
            loadImagesForStatuses(items);
        }

        return response.isSuccess();
    }

}
