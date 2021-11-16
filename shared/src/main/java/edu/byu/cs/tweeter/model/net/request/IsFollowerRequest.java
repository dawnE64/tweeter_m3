package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class IsFollowerRequest {
    AuthToken authToken;
    User targetUser;
    User potentialFollower;

    private IsFollowerRequest() {}

    public IsFollowerRequest(AuthToken authToken, User targetUser, User potentialFollower) {
        this.authToken = authToken;
        this.targetUser = targetUser;
        this.potentialFollower = potentialFollower;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public User getPotentialFollower() {
        return potentialFollower;
    }

    public void setPotentialFollower(User potentialFollower) {
        this.potentialFollower = potentialFollower;
    }
}
