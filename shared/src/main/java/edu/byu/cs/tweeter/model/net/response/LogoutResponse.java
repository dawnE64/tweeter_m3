package edu.byu.cs.tweeter.model.net.response;

public class LogoutResponse extends Response {
    // Returns no information

    // Unsuccessful
    public LogoutResponse(String message) {
        super(false, message);
    }

    // Successful
    public LogoutResponse(boolean success) {
        super(true);
    }

}
