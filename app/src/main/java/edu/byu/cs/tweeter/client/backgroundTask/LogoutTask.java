package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AlreadyAuthenticatedTask {
    private static final String LOG_TAG = "LogoutTask";
    static final String URL_PATH = "/logout";

    public LogoutTask(Handler messageHandler, AuthToken authToken) {
        super(messageHandler, authToken);
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        LogoutRequest request = new LogoutRequest(authToken);
        LogoutResponse response = getServerFacade().logout(request, URL_PATH);

        return response.isSuccess();
    }
}
