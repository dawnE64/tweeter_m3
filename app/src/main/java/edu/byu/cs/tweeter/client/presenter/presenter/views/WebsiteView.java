package edu.byu.cs.tweeter.client.presenter.presenter.views;

import edu.byu.cs.tweeter.model.domain.Status;

// Includes all interfaces and finally the website nav!!
public interface WebsiteView extends PagedView<Status> {
    void navigateToWebsite(String clickable);
}
