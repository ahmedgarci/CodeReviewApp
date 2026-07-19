package com.example.CodeReviewApp.dto.Submissions.Out;

import java.util.List;

import com.example.CodeReviewApp.dto.Comments.OUt.CommentResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileContentResponseDto {
    
    private byte[] content;
    private List<CommentResponseDto> comments;
}
