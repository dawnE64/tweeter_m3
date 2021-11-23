package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.handler.PagedTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryService extends BaseService {
    private PagedObserver<Status> observer;
    private ServerFacade serverFacade;

    public StoryService(PagedObserver<Status> observer) {
        this.observer = observer;
    }

    public void getStory(AuthToken authToken, User targetUser, int numItemsToGet,
                         Status lastStatus) {
        GetStoryTask getStoryTask = new GetStoryTask(new PagedTaskHandler<Status>(getObserver()) {
        }, authToken, targetUser, numItemsToGet, lastStatus, getServerFacade());
        super.executeService(getStoryTask);
    }

    public PagedObserver<Status> getObserver() {
        return observer;
    }

    // For testing! We need to be able to intervene and insert our own facade
    public ServerFacade getServerFacade() {
        if (serverFacade == null) {
            serverFacade = new ServerFacade();
        }
        return serverFacade;
    }

}
