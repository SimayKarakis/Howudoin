package edu.sabanciuniv.howudoin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.web.filter.OncePerRequestFilter; // ???
import org.springframework.security.core.userdetails.UserDetailsService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;



@Component
public class JwtAuthFilter extends OncePerRequestFilter
{
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelperUtils jwtHelperUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // JWT authentication filter logic
    }
}
