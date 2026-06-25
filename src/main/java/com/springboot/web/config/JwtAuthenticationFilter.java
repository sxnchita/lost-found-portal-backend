package com.springboot.web.config;

import com.springboot.web.service.CustomUserDetailsService;
import com.springboot.web.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        System.out.println("Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken =
                		package com.springboot.web.config;

                import com.springboot.web.service.CustomUserDetailsService;
                import com.springboot.web.service.JwtService;
                import jakarta.servlet.FilterChain;
                import jakarta.servlet.ServletException;
                import jakarta.servlet.http.HttpServletRequest;
                import jakarta.servlet.http.HttpServletResponse;
                import org.springframework.beans.factory.annotation.Autowired;
                import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
                import org.springframework.security.core.context.SecurityContextHolder;
                import org.springframework.security.core.userdetails.UserDetails;
                import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
                import org.springframework.stereotype.Component;
                import org.springframework.web.filter.OncePerRequestFilter;

                import java.io.IOException;

                @Component
                public class JwtAuthenticationFilter extends OncePerRequestFilter {

                    @Autowired
                    private JwtService jwtService;

                    @Autowired
                    private CustomUserDetailsService customUserDetailsService;

                    @Override
                    protected void doFilterInternal(
                            HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain filterChain
                    ) throws ServletException, IOException {

                        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                            filterChain.doFilter(request, response);
                            return;
                        }

                        final String authHeader = request.getHeader("Authorization");
                        System.out.println("Authorization Header: " + authHeader);

                        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                            filterChain.doFilter(request, response);
                            return;
                        }

                        String token = authHeader.substring(7);
                        String email = jwtService.extractEmail(token);

                        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                            if (jwtService.validateToken(token, userDetails)) {
                                UsernamePasswordAuthenticationToken authenticationToken =
                                        new UsernamePasswordAuthenticationToken(
                                                userDetails,
                                                null,
                                                userDetails.getAuthorities()
                                        );

                                authenticationToken.setDetails(
                                        new WebAuthenticationDetailsSource().buildDetails(request)
                                );

                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                System.out.println("JWT authentication successful for: " + email);
                            } else {
                                System.out.println("JWT token validation failed");
                            }
                        }

                        filterChain.doFilter(request, response);
                    }
                }  new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                System.out.println("JWT authentication successful for: " + email);
            } else {
                System.out.println("JWT token validation failed");
            }
        }

        filterChain.doFilter(request, response);
    }
}