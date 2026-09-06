package com.example.CodeReviewApp.dto.Submissions.Out;

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
public class QualityTestResultDto {
    
    private Integer bugs;
    private Integer vulnerabilities;
    private Integer codeSmells;
    private Integer coverage;
}
