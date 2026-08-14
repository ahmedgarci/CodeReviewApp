package com.example.CodeReviewApp.dto.Submissions.In;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishReviewDto {
    @NotBlank
    private String description;

    @NotBlank
    private String title;

    @Size(min = 1)
    private List<Long> reviewers;

}
