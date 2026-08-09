package com.example.CodeReviewApp.util.Listener.In;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewFailureEvent {
    private Long executionId;
    private String errorMessage;

}
