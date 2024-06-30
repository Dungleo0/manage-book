package com.example.app.config.filter;



import com.example.app.dto.auth.UserAuthentication;
import com.example.app.exception.AuthException;
import com.example.app.util.AuthUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class JwtFilterCofig extends OncePerRequestFilter {


    private ObjectMapper objectMapper;

    @Value("${app.secret-key}")
    public String secretKey;

    public JwtFilterCofig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasLength(authorization) && authorization.startsWith("Bearer ")) {
            String token = authorization.replace("Bearer ", "");

            try {
                Map<String, Object> payload = AuthUtil.getPayloadJwt(token, secretKey);
                UserAuthentication user = objectMapper.convertValue(payload, UserAuthentication.class);

                List<SimpleGrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority(user.getRole().toString()));

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, roles);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthException e) {
                // Handle AuthException and respond with a 401 status code or any other appropriate action
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            } catch (Exception e) {
                // Handle any other exceptions and respond with a 500 status code or any other appropriate action
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
