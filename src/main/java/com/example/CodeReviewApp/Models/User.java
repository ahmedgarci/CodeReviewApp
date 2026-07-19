package com.example.CodeReviewApp.Models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDate created_at;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getUsername(){
        return this.email;
    }
    
    public String getName(){ return this.username;}


}
