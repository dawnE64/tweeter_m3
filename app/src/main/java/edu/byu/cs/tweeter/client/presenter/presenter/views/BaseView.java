package edu.byu.cs.tweeter.client.presenter.presenter.views;

public interface BaseView {
    void displayInfoMessage(String message);
    void clearInfoMessage();

    void displayErrorMessage(String message);
    void clearErrorMessage();
}
