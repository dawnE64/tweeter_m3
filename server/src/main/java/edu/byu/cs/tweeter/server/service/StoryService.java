package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

public class StoryService {

    public GetStoryResponse getStory(GetStoryRequest request) {
        return new StoryDAO().getStory(request);
    }

}
