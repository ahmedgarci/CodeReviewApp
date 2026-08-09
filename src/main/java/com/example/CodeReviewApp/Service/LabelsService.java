package com.example.CodeReviewApp.Service;

import java.util.List;

import com.example.CodeReviewApp.dto.Labels.out.LabelDto;

public interface LabelsService {

    List<LabelDto> getLabels();
}
