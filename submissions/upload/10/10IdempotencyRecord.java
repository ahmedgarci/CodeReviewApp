package com.example.CodeReviewApp.security.Cache;

public record IdempotencyRecord(
    Integer httpStatus,Object response
) {
    
}
