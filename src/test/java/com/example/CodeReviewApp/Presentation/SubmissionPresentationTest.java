package com.example.CodeReviewApp.Presentation;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.example.CodeReviewApp.Models.Enums.SubmissionStatus;
import com.example.CodeReviewApp.Service.RepoReviewService;
import com.example.CodeReviewApp.Service.SubmissionCreationService;
import com.example.CodeReviewApp.Service.SubmissionDetailsService;
import com.example.CodeReviewApp.dto.Submissions.Out.FileContentResponseDto;
import com.example.CodeReviewApp.dto.Submissions.Out.ProjectSubmissionsDto;
import com.example.CodeReviewApp.dto.Submissions.Out.SubmissionDetailsDto;
import com.example.CodeReviewApp.security.JwtService;

@WebMvcTest(SubmissionPresentation.class)
@AutoConfigureMockMvc(addFilters = false)
public class SubmissionPresentationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private SubmissionCreationService submissionCreationService;
    @MockBean
    private SubmissionDetailsService submissionDetailsService;
    @MockBean
    private RepoReviewService repoReviewService;

    @Test
    public void shouldCreateSubmission() throws Exception{

        
        MockMultipartFile data = new MockMultipartFile(
            "data",
            "",
            MediaType.APPLICATION_JSON_VALUE,
            """
            {
                "description": "desc",
                "title": "title",
                "reviewers": [1, 2]
            }
            """.getBytes()
        );

        MockMultipartFile files = new MockMultipartFile(
            "files",
            "Main.java",
            MediaType.TEXT_PLAIN_VALUE,
            "public class Main {}".getBytes()
        );

        mockMvc.perform(multipart("/submission/10").file(data).file(files)).andExpect(status().isCreated());
        
        verify(submissionCreationService).publishCode(org.mockito.ArgumentMatchers.any(),org.mockito.ArgumentMatchers.anyList(),org.mockito.ArgumentMatchers.eq(10L));        
        
    }

    public void shouldAnalayzeFullRepo()throws Exception{
        
        mockMvc.perform(post("/repo/1")).andExpect(status().isOk());

        verify(repoReviewService).analyze(1L);

    }

    @Test
    public void shouldGetProjectSubmissions()throws Exception{

        List<ProjectSubmissionsDto> expected = List.of(new ProjectSubmissionsDto()); 

        Long projectId = 1L;

        when(submissionDetailsService.getProjectSubmissions(projectId)).thenReturn(expected);

        mockMvc.perform(get("/submission/{projectId}",projectId)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$.length()").value(1));

        verify(submissionDetailsService).getProjectSubmissions(projectId);

    }

    @Test
    public void shouldGetSubmissionDetails()throws Exception{

        Long submissionId  = 10L;

        SubmissionDetailsDto detailsDto = new SubmissionDetailsDto();

        when(submissionDetailsService.getSubmissionDetails(submissionId)).thenReturn(detailsDto);

        mockMvc.perform(get("/submission/{submissionId}/details",submissionId)).andExpect(status().isOk());

        verify(submissionDetailsService).getSubmissionDetails(submissionId);

    }

    @Test
    public void shouldGetFileContent()throws Exception{

        Long submissionId  = 10L;

        Long fileId = 11L;

        FileContentResponseDto fileContent = new FileContentResponseDto();

        when(submissionDetailsService.getFileContent(submissionId,fileId)).thenReturn(fileContent);

        mockMvc.perform(get("/submission/{submissionId}/file/{fileId}",submissionId,fileId)).andExpect(status().isOk());

        verify(submissionDetailsService).getFileContent(submissionId, fileId);

    }

    @Test
    public void shouldUpdateSubmissionStatus()throws Exception{

        Long submissionId  = 10L;

        mockMvc.perform(post("/submission/status/{submissionId}",submissionId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(
            """
                        {
                            "status": "APPROVED"
                        }
            """
        ))        
        .andExpect(status().isOk());

        verify(submissionDetailsService).updateSubmissionStatus(eq(10L), eq(SubmissionStatus.APPROVED));


    }



}


// @PostMapping("/status/{submissionId}")
// public ResponseEntity<Void> updateSubmissionStatus(@RequestBody SubmissionStatusDto submissionDto,@PathVariable Long submissionId) {
    
//     submissionDetailsService.updateSubmissionStatus(submissionId, submissionDto.status());

//     return ResponseEntity.ok().build();
// }


