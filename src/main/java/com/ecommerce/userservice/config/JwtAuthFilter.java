package com.ecommerce.userservice.config;

import com.ecommerce.userservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserDetailsServiceImp userDetailsServiceImpl;

    private final String LOGIN_URI = "/users/api/v1/login";  // Replace with your actual login URL pattern

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Check if request is for login or bypass filtering

        if (isLoginPage(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        System.out.println("Authentication header:" + authHeader);

        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);

            System.out.print("Username :"+username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
              //  List<String> roles = jwtService.extractRoles(token);
                List<String> roles = new ArrayList<>();
                roles.add("ROLE_ADMIN");
                System.out.print("Roles ==================================>"+roles);
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isLoginPage(HttpServletRequest request) {
        return request.getRequestURI().equals(LOGIN_URI);
    }
}

