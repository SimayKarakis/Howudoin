package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.LoginRequest;
import edu.sabanciuniv.howudoin.model.LoginResponse;
import edu.sabanciuniv.howudoin.security.JwtHelperUtils; // additional
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController
{
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelperUtils jwtHelperUtils; // I've changed this part form JWT Helper to JWT Helper utils

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
    {
        this.doAuthenticate(loginRequest.getEmail(), loginRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = this.jwtHelperUtils.generateToken(userDetails);
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .email(userDetails.getUsername())
                .build();
        return ResponseEntity.ok(loginResponse);
    }

    private void doAuthenticate(String email, String password)
    {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try
        {
            authenticationManager.authenticate(authenticationToken);
        }
        catch(BadCredentialsException e)
        {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
