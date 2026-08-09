package com.example.CodeReviewApp.Repo;

import java.util.List;

import com.example.CodeReviewApp.dto.Labels.out.LabelDto;

public interface LabelRepository {

    List<LabelDto> getAll();
}
