package edu.sabanciuniv.howudoin.service.implementation;

import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import edu.sabanciuniv.howudoin.service.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsersImplementation implements UsersService
{
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void registerUser(Users user) throws Exception // Where the fuck this class come from?!
    {
        /*
        Users newUser = new Users();

        newUser.setId(user.getId());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        */
        System.out.println("User saved to MongoDB successfully.");
        usersRepository.save(user);
    }
}
