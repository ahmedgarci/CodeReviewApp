package com.example.CodeReviewApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterChainConfig {

    private final CorsConfigurationSource corsConfig;
    private final JwtFilter jwtFilter;
    
    @Bean
    public SecurityFilterChain filter(HttpSecurity http)throws Exception{
        http
            .cors(c -> c.configurationSource(corsConfig))
            .csrf(csrf -> csrf.disable())
//            .exceptionHandling((ex)-> ex.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
            .authorizeHttpRequests((request) -> request.requestMatchers(
                    "/auth/**" ,"/ws/**"
                                                        ).permitAll()
                                                        .anyRequest()
                                                        .authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        .logout(logout -> logout.logoutUrl("/logout").addLogoutHandler(logoutHandlerImpl));


        return http.build();
    }
}
