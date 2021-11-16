package edu.byu.cs.tweeter.model.net.response;

public class UnfollowResponse extends Response {

    // just message for fail
    public UnfollowResponse(String message) {
        super(false, message);
    }

    // Just boolean for that
    public UnfollowResponse(boolean success) {
        super(success, null);
    }
}
