package com.example.CodeReviewApp.security.Cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.CodeReviewApp.Service.IdempotencyService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Aspect
@Service
@RequiredArgsConstructor
public class IdempotencyAspect {

    private final IdempotencyService idempotencyService;
    
    @Around("@annotation(Idempotent)")
    public Object checkIdempotency(ProceedingJoinPoint joinPoint) throws Throwable{
        
        ServletRequestAttributes attributes  = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        String idempotencyKey = request.getHeader("Idempotency-Key");

        IdempotencyRecord cached =  idempotencyService.get(idempotencyKey);

        if(cached != null){
            return cached.response();
        }

        Object response = joinPoint.proceed();

        idempotencyService.set(idempotencyKey, new IdempotencyRecord(201, response));

        return response;
    }
    
}
