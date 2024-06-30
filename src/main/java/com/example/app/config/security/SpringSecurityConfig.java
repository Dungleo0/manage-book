package com.example.app.config.security;


import com.example.app.config.filter.JwtFilterCofig;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

    public JwtFilterCofig jwtFilterCofig;

    public SpringSecurityConfig(JwtFilterCofig jwtFilterCofig) {
        this.jwtFilterCofig = jwtFilterCofig;
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(res ->res.disable())
                .authorizeHttpRequests(res -> res.requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/account/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(jwtFilterCofig, BasicAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/api/v1/auth/**")
                .requestMatchers("/api/v1/account/**");
    }
}
