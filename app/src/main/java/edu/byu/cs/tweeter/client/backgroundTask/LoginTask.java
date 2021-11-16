package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends NeedsAuthenticationTask {

    private static final String LOG_TAG = "LoginTask";
    static final String URL_PATH = "/login";

    public LoginTask(String alias, String password, Handler messageHandler) {
        super(messageHandler, alias, password);
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        LoginRequest request = new LoginRequest(alias, password);
        LoginResponse response = getServerFacade().login(request, URL_PATH);

        if (response.isSuccess()) {
            this.user = response.getUser();
            this.authToken = response.getAuthToken();
            BackgroundTaskUtils.loadImage(user);
        }

        return response.isSuccess();
    }
}
