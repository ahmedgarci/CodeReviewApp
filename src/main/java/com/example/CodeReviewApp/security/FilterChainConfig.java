package com.example.CodeReviewApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.CodeReviewApp.security.Auth.RestAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterChainConfig {

    private final CorsConfigurationSource corsConfig;
    private final JwtFilter jwtFilter;
    private final LogoutHandler logoutHandlerImpl;
    
    @Bean
    public SecurityFilterChain filter(HttpSecurity http)throws Exception{
        http
            .cors(c -> c.configurationSource(corsConfig))
            .csrf(csrf -> csrf.disable())
            .exceptionHandling((ex)-> ex.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
            .authorizeHttpRequests((request) -> request.requestMatchers(
                    "/auth/**" ,"/ws/**"
                                                        ).permitAll()
                                                        .anyRequest()
                                                        .authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout.logoutUrl("/logout").addLogoutHandler(logoutHandlerImpl)
        .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
        );


        return http.build();
    }
}
