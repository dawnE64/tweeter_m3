package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedTask<User> {
    private static final String LOG_TAG = "GetFollowingTask";
    static final String URL_PATH = "/getfollowing";

    private ServerFacade serverFacade;

    public GetFollowingTask(Handler messageHandler, AuthToken authToken, User targetUser, int limit, User lastItem) {
        super(messageHandler, authToken, targetUser, limit, lastItem);
    }

    public ServerFacade getServerFacade() {
        if( serverFacade == null ) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }

    @Override
    protected boolean runTask() { // identical to getFollowersTask's, fix when implmnt server
        String userAlias = targetUser.getAlias();

        String lastItemAlias = "";
        if (lastItem != null) {
            lastItemAlias = lastItem.getAlias();
        }

        FollowingRequest request = new FollowingRequest(authToken, userAlias, limit, lastItemAlias);

        try {
            FollowingResponse response = getServerFacade().getFollowees(request, URL_PATH);

            if(response.isSuccess()) {
                items = response.getFollowees();
                hasMorePages = response.getHasMorePages();

                loadImages(response.getFollowees());
                sendSuccessMessage();
            }
            else {
                sendFailedMessage();
            }
        } catch (IOException | TweeterRemoteException ex) {
//            Log.e(LOG_TAG, "Failed to get followees", ex);
            sendExceptionMessage(ex);
        }
        return true;

        /* MY OLD CODE
        Pair<List<User>, Boolean> pageOfUsers = getFakeData().getPageOfUsers((User) lastItem, limit, targetUser);
        this.items = pageOfUsers.getFirst();
        this.hasMorePages = pageOfUsers.getSecond();

        for (User u : this.items) {
            BackgroundTaskUtils.loadImage(u);
        }
        return true;
         */
    }

    // This method is public so it can be accessed by test cases
    public void loadImages(List<User> followees) throws IOException {
        for (User u : followees) {
            BackgroundTaskUtils.loadImage(u);
        }
    }

}