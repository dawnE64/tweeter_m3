package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.server.util.FakeData;
import edu.byu.cs.tweeter.server.util.Pair;

public class FeedDAO {

    public GetFeedResponse getFeed(GetFeedRequest request) {
        Pair<List<Status>, Boolean> pageOfStatus = getFakeData().getPageOfStatus(request.getLastItem(), request.getLimit());
        // fixme: if errors crop up make sure its not because this should be in the shared folder?

        return new GetFeedResponse(pageOfStatus.getFirst(), pageOfStatus.getSecond());
    }

    FakeData getFakeData() {
        return new FakeData();
    }

}
