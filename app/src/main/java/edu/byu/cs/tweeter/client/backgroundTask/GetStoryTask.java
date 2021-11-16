package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedTask<Status> {
    private static final String LOG_TAG = "GetStoryTask";
    static final String URL_PATH = "/getstory";

    public GetStoryTask(Handler messageHandler, AuthToken authToken, User targetUser, int limit, Status lastItem) {
        super(messageHandler, authToken, targetUser, limit, lastItem);
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        GetStoryRequest request = new GetStoryRequest(authToken, targetUser, limit, lastItem);
        GetStoryResponse response = getServerFacade().getStory(request, URL_PATH);

        if (response.isSuccess()) {
            items = response.getStory();
            hasMorePages = response.getHasMorePages();
            loadImagesForStatuses(items);
        }

        return response.isSuccess();
    }

}