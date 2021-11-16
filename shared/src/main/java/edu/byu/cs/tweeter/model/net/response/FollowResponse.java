package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowResponse extends Response {
    public FollowResponse(String message) {
        super(false, message);
    } // fixme: Good chance this will return null, so keep an eye on that.

    public FollowResponse(boolean success) {
        super(success, null);
    }
}
