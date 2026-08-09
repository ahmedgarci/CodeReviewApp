package com.example.CodeReviewApp.util.Producer;

import com.example.CodeReviewApp.util.Producer.out.Event;

public interface EventProducer {
    void execute(Event event);
}
