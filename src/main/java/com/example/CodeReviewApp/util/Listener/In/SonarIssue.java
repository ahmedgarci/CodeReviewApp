package com.example.CodeReviewApp.util.Listener.In;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SonarIssue {

    private String severity;
    private String component;
    private Integer line;
    private String message;
    private String type;

}