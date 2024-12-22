package edu.sabanciuniv.howudoin.service;

import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users user = usersRepository.findByEmail(username);
            String email = user.getEmail();
            String password = user.getPassword();
            return new User(email, password, new ArrayList<>());
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found: " + e.toString());
        }
    }
}