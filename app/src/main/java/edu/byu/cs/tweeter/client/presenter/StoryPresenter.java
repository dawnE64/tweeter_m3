package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.client.presenter.presenter.views.WebsiteView;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status> implements PagedObserver<Status>, GetUserObserver {

    private WebsiteView view;
    StoryService storyService;

    public StoryPresenter(WebsiteView view, AuthToken authToken, User targetUser) {
        super(view, authToken, targetUser);
        this.view = view;
    }

    @Override
    protected void runGetService() {
        getStoryService().getStory(authToken, targetUser, PAGE_SIZE, lastItem);
    }

    @Override
    public void serviceFailure(String message) {
        view.displayErrorMessage(message);
    }

    public void evaluateClickedItem(String clickable) {
        if (clickable.contains("http")) {
            view.navigateToWebsite(clickable);
        } else {
            goToUser(clickable);
        }
    }

    public StoryService getStoryService() {
        if (storyService == null) {
            storyService = new StoryService(this);
        }
        return storyService;
    }

}
