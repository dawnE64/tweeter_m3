package edu.byu.cs.tweeter.model.net.response;

public class GetCountResponse extends Response {
    private int followersCount;
    private int followingCount;

    public GetCountResponse(String message) {
        super(false, message);
    }

    public GetCountResponse(int followersCount, int followingCount) {
        super(true, null);
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }
}
