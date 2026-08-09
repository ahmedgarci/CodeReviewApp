package com.example.CodeReviewApp.Repo.Implementations;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Repo.LabelRepository;
import com.example.CodeReviewApp.dto.Labels.out.LabelDto;

import static com.example.jooq.tables.Labels.LABELS;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LabelRepositoryImpl implements LabelRepository {

    private final DSLContext dsl;

    @Override
    public List<LabelDto> getAll() {

        return dsl.select(LABELS.ID,LABELS.NAME).from(LABELS).fetchInto(LabelDto.class);

    }
    
}
