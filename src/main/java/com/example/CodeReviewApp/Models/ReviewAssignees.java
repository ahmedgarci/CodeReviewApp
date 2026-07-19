package com.example.CodeReviewApp.Models;

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
public class ReviewAssignees {
    

    private Long id; 
    private Long review_id;
    private Long reviewer_id;
}
