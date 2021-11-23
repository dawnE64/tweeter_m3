package edu.byu.cs.tweeter.client.server;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class StoryServiceIntegrationTest {
    private StoryService storyServiceSpy;
    private StoryServiceObserver mockObserver;
    private GetStoryResponse successResponse;
    private User user1;

    private CountDownLatch countDownLatch;

    @Before
    public void setup() {
        mockObserver = new StoryServiceObserver();

        user1 = getFakeData().getFirstUser();
        List<Status> story1 = getFakeData().getPageOfStatus(null, 10).getFirst();
        successResponse = new GetStoryResponse(story1, false);

        resetCountDownLatch();
    }

    private FakeData getFakeData() {
        return new FakeData();
    }

    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }

    private class StoryServiceObserver implements PagedObserver<Status> {
        private boolean success;
        private String message;
        private List<Status> story;
        private boolean hasMorePages;
        private String exceptionMessage;

        @Override
        public void pagedSuccess(List<Status> items, boolean hasMorePages) {
            this.success = true;
            this.message = null;
            this.story = items;
            this.hasMorePages = hasMorePages;
            this.exceptionMessage = null;

            countDownLatch.countDown();
        }

        @Override
        public void serviceFailure(String message) {
            this.success = false;
            this.message = message;
            this.story = null;
            this.hasMorePages = false;
            this.exceptionMessage = null;

            countDownLatch.countDown();
        }

        @Override
        public void serviceException(String message) {
            this.success = false;
            this.message = null;
            this.story = null;
            this.hasMorePages = false;
            this.exceptionMessage = message;

            countDownLatch.countDown();
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public List<Status> getStory() {
            return story;
        }

        public boolean getHasMorePages() {
            return hasMorePages;
        }

        public String getExceptionMessage() {
            return exceptionMessage;
        }

    }

    private StoryService setupStoryServiceSpy(GetStoryResponse serverFacadeResponse) {
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        try {
            Mockito.when(mockServerFacade.getStory(Mockito.any(), Mockito.any())).thenReturn(serverFacadeResponse);
        } catch (Exception e) {
            // We won't actually get an exception while setting up the mock
        }

        StoryService storyService = new StoryService(mockObserver);
        StoryService storyServiceSpy = Mockito.spy(storyService);
        Mockito.when(storyServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

        return storyServiceSpy;
    }

    @Test
    public void testStoryService_getStorySucceeds() throws InterruptedException {
        StoryService storyServiceSpy = setupStoryServiceSpy(successResponse);

        storyServiceSpy.getStory(new AuthToken(), user1, 10, null);
        awaitCountDownLatch();

//        Mockito.verify(mockObserver)
        assert(successResponse.equals(mockObserver));
    }

}
