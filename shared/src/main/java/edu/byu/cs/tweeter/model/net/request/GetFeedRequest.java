package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFeedRequest {
    AuthToken authToken;
    User targetUser;
    int limit;
    Status lastItem;

    private GetFeedRequest() {}

    public GetFeedRequest(AuthToken authToken, User targetUser, int limit, Status lastItem) {
        this.authToken = authToken;
        this.targetUser = targetUser;
        this.limit = limit;
        this.lastItem = lastItem;
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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Status getLastItem() {
        return lastItem;
    }

    public void setLastItem(Status lastItem) {
        this.lastItem = lastItem;
    }
}
