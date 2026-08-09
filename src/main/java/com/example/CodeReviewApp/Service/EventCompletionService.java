package com.example.CodeReviewApp.Service;

import com.example.CodeReviewApp.util.Listener.In.ReviewCompletedEvent;
import com.example.CodeReviewApp.util.Listener.In.ReviewFailureEvent;

public interface EventCompletionService {
    void handleSuccess(ReviewCompletedEvent event);
    void handleFailure(ReviewFailureEvent  event);
}
