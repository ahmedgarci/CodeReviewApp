package com.example.CodeReviewApp.Service.Implementations;

import org.springframework.stereotype.Service;

import com.example.CodeReviewApp.Service.IdempotencyService;
import com.example.CodeReviewApp.security.Cache.IdempotencyRecord;
import com.github.benmanes.caffeine.cache.Cache;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdempotencyServiceImpl implements IdempotencyService{
    
    private final Cache<String,IdempotencyRecord> cache;


    public IdempotencyRecord get(String key){

        return cache.getIfPresent(key);
    }

    public void set(String key,IdempotencyRecord value){

        cache.put(key, value);

    }

}
