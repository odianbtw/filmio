package com.paradigma.filmio.core.config;


import com.paradigma.filmio.core.security.JwtAuthenticationFilter;
import com.paradigma.filmio.core.service.AccessTokenGenerator;
import com.paradigma.filmio.core.service.AuthenticationTokenUtil;
import com.paradigma.filmio.core.service.JwtAuthenticationUtil;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(
            AuthenticationTokenUtil util
    ){
        return new JwtAuthenticationFilter(util);
    }

    @Bean
    Key key(@Value("${jwt.secret}") String secret) {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationTokenUtil util) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/v1/auth/**").permitAll()
                        .anyRequest().permitAll()
                )
//                .addFilterBefore(jwtAuthenticationFilter(util), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
