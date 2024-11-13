package edu.sabanciuniv.howudoin.service;

import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsersService
{
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    public void registerUser(Users user) throws Exception    // long in security page, check it out!!!
    {
        usersRepository.save(user);
        System.out.println("User saved to MongoDB successfully.");
    }

    /*
    public Users getUser(String email, String password)
    {
        return UsersRepository.find();
    }
    */
}
