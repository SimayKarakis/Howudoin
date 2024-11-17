package edu.sabanciuniv.howudoin.security;

import org.springframework.stereotype.Component;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint //???
{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println(authException.getMessage());
    }
}