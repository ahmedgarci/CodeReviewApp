package com.example.CodeReviewApp.Presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CodeReviewApp.Service.CommentService;
import com.example.CodeReviewApp.dto.Comments.In.CreateCommentDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/comments")
public class CommentsPresentation {

    private final CommentService commentService;
    

    @PostMapping("/{submissionId}/{codeId}")
    public ResponseEntity<Void> createComment(@RequestBody CreateCommentDto dto,@PathVariable Long submissionId,@PathVariable Long codeId) {

        commentService.create(dto, submissionId, codeId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    
    


}
