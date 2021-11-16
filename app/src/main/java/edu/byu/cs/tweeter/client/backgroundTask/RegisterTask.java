package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends NeedsAuthenticationTask {

    private static final String LOG_TAG = "RegisterTask";
    static final String URL_PATH = "/register";

    /**
     * The user's first name.
     */
    private String firstName;
    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private String image;

    public RegisterTask(String firstName, String lastName, String alias, String password,
                        String image, Handler messageHandler) {
        super(messageHandler, alias, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    @Override
    protected boolean runTask() throws IOException, TweeterRemoteException {
        RegisterRequest request = new RegisterRequest(firstName, lastName, alias, password, image);
        RegisterResponse response = getServerFacade().register(request, URL_PATH);

        this.user = response.getUser();
        this.authToken = response.getAuthToken();

        loadImage(user);

        return true; // for now
    }
}
