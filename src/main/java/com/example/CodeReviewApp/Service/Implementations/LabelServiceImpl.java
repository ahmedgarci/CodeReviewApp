package com.example.CodeReviewApp.Service.Implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Repo.LabelRepository;
import com.example.CodeReviewApp.Service.LabelsService;
import com.example.CodeReviewApp.dto.Labels.out.LabelDto;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelsService {

    private final LabelRepository labelRepository;

    @Override
    public List<LabelDto> getLabels() {

        return labelRepository.getAll();

    }
    
}
