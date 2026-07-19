package com.example.CodeReviewApp.Service;

import com.example.CodeReviewApp.security.Cache.IdempotencyRecord;

public interface IdempotencyService {
    
    IdempotencyRecord get(String key);
    void set(String key,IdempotencyRecord value);
}
