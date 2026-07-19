package com.example.CodeReviewApp.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
    
    
        String header = request.getHeader("Authorization");
    
        if(header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
    
    
        try {
    
            String token = header.substring(7);
    
            String username = jwtService.getSubject(token);
    
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    
                UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);
    
                if(jwtService.isTokenValid(token,user.getUsername())) {
    
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
    
    
        } catch(JwtException e) {
            
            SecurityContextHolder.clearContext();
        }
    
    
        filterChain.doFilter(request,response);
    }
}
