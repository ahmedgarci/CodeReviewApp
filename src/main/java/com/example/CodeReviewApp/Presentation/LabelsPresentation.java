package com.example.CodeReviewApp.Presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CodeReviewApp.Service.LabelsService;
import com.example.CodeReviewApp.dto.Labels.out.LabelDto;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@RequestMapping(value = "/labels")
@RestController
public class LabelsPresentation {
    
    private final LabelsService labelsService;

    @GetMapping()
    public ResponseEntity<List<LabelDto>> getLabels() {

        return ResponseEntity.ok().body(labelsService.getLabels());
    }
    
    
}
