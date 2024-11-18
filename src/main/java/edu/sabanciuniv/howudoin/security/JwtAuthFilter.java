package edu.sabanciuniv.howudoin.security;

import edu.sabanciuniv.howudoin.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


@Component
public class JwtAuthFilter extends OncePerRequestFilter // ???
{
    private Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtHelperUtils jwtHelperUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestPath = request.getServletPath();
        System.out.println("Intercepted request: " + requestPath);

        // Skip JWT validation for public endpoints
        if (requestPath.equals("/register") || requestPath.equals("/login")) {
            System.out.println("Public endpoint accessed: " + requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("JWT validation required for endpoint: " + requestPath);

        String token = getTokenFromRequest(request);
        if (token != null) {
            System.out.println("Token found: " + token);

            String username = jwtHelperUtils.getUsernameFromToken(token);
            if (username != null) {
                System.out.println("Username extracted from token: " + username);

                var userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtHelperUtils.validateToken(token, userDetails)) {
                    System.out.println("Token validated successfully for user: " + username);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("Token validation failed for user: " + username);
                }
            } else {
                System.out.println("Unable to extract username from token.");
            }
        } else {
            System.out.println("No token found in request.");
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
}
