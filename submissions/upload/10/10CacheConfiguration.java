package com.example.CodeReviewApp.security.Cache;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


@Configuration
public class CacheConfiguration {

    @Bean
    public Cache<String,IdempotencyRecord> cacheSetUp(){

        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofHours(2))
                .maximumSize(1000)
           .build();

    }
    
}
