package com.example.CodeReviewApp.Presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.CodeReviewApp.Service.CommentService;
import com.example.CodeReviewApp.dto.Comments.In.CreateCommentDto;
import com.example.CodeReviewApp.security.JwtService;


@WebMvcTest(CommentsPresentation.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentPresentationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CommentService commentService;

    @Test
    public void shouldCreateComment()throws Exception{

        Long submissionId = 1L , codeId = 2L;

        mockMvc.perform(post("/comments/{submissionId}/{codeId}",submissionId,codeId).contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "line_number": 1, 
                    "content" : "content"
                }
                """)).andExpect(status().isCreated());
        
        verify(commentService).create(any(CreateCommentDto.class), eq(submissionId), eq(codeId));

    }
    @Test
    void shouldReturn400WhenSubmissionIdIsNotPositive() throws Exception {

    mockMvc.perform(
            post("/comments/{submissionId}/{codeId}", 0, 2)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "line_number": 1,
                            "content": "content"
                        }
                    """)
    )
    .andExpect(status().isBadRequest());

    verify(commentService, never()).create(any(CreateCommentDto.class), anyLong(), anyLong());
    
}
  
}
