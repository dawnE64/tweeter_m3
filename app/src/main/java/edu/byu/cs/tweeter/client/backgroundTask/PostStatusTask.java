package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

/**
 * Background task that posts a new status sent by a user.
 */
public class PostStatusTask extends AlreadyAuthenticatedTask {
    private static final String LOG_TAG = "PostStatusTask";
    static final String URL_PATH = "/poststatus";
    private Status status;

    public PostStatusTask(Handler messageHandler, AuthToken authToken, Status status) {
        super(messageHandler, authToken);
        this.status = status;
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        PostStatusRequest request = new PostStatusRequest(authToken, status);
        PostStatusResponse response = getServerFacade().postStatus(request, URL_PATH);

        return response.isSuccess();
    }
}
