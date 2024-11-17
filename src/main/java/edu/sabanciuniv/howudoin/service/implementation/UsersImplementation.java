package edu.sabanciuniv.howudoin.service.implementation;

// in the security file there is something like "imports **" here, what does it mean?

import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import edu.sabanciuniv.howudoin.service.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsersImplementation implements UsersService
{
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void registerUser(Users user) throws Exception
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

    @Override
    public List<Users> searchCustomUser(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return usersRepository.findByCreatedDate(start, end);
    }
}
