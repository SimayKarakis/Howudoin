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
import java.util.ArrayList;


@Component
public class JwtAuthFilter extends OncePerRequestFilter // ???
{
    private Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtHelperUtils jwtHelperUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // JWT authentication filter logic
        /*
        // Step 1: Get the JWT token from the Authorization header
        String token = getTokenFromRequest(request);

        if (token != null && jwtHelperUtils.validateToken(token, userDetailsService.loadUserByUsername(jwtHelperUtils.getUsernameFromToken(token)))) {
            String username = jwtHelperUtils.getUsernameFromToken(token);

            // Step 3: Set the authentication in the security context
            // Create an authentication object and add it to the SecurityContext
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, new ArrayList<>()); // Assuming no roles for now, can be changed based on your logic

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Step 4: Continue the filter chain
        filterChain.doFilter(request, response);
        */
    }
    /*
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extract token after "Bearer "
        }

        return null;
    }
    */

}
