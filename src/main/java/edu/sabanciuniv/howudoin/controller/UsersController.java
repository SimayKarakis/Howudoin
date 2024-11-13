package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController
{
    @Autowired
    private UsersService usersService;

    //API Endpoint for registering user
    @PostMapping("/register")
    public boolean register(@RequestBody Users user) throws Exception // I'm returning boolean value here. In the security file it returns user object!!!
    {
        usersService.registerUser(user);
        System.out.println("User" + user.getName() + user.getLastName() + "is registered.");
        return true;
    }


}
