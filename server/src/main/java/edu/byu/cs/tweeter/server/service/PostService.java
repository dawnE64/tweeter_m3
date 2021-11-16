package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

public class PostService {

    public PostStatusResponse post(PostStatusRequest request) {
        // Todo: Add real functionality. Also no DAO!
        return new PostStatusResponse(true);
    }

}
