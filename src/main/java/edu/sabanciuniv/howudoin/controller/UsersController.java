package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import edu.sabanciuniv.howudoin.service.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController
{
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;

    //API Endpoint for registering user
    @PostMapping("/register")
    public boolean register(@RequestBody Users user) throws Exception // I'm returning boolean value here. In the security file it returns user object!!!
    {
        usersService.registerUser(user);
        System.out.println("User" + user.getName() + user.getLastName() + "is registered.");
        return true;
    }

    @GetMapping("/custom-date-search") // should I add this??
    public List<Users> searchCustomAccount(@RequestParam String startDate, @RequestParam String endDate) {
        return usersService.searchCustomUser(startDate, endDate);
    }

    @PostMapping("/friends/add")
    public boolean sendRequest(@RequestBody int fromUserId, @RequestBody int toUserId)
    {
        Users toUser = usersRepository.findById(toUserId).orElse(null);
        Users fromUser = usersRepository.findById(fromUserId).orElse(null);

        if(toUser == null || fromUser == null)
        {
            System.out.println("User not found");
            return false;
        }
        else if (toUser.equals(fromUser))
        {
            System.out.println("You cannot send a friend request to yourself.");
            return false;
        }
        else if (fromUser.getOutGoingFriendRequestsList().contains(toUser) || toUser.getFriendsList().contains(fromUser)) {
            System.out.println("Friend request already sent or you are already friends.");
            return false;
        }
        else
        {
            toUser.getInComingFriendRequestsList().add(fromUser);
            fromUser.getOutGoingFriendRequestsList().add(toUser);
            System.out.println("Friend request sent to" + toUser.getName() + ".");
            return true;
        }
    }

    @PostMapping("/friends/accept")
    public boolean acceptRequest(@RequestBody int fromUserId, @RequestBody int toUserId) throws Exception
    {
        Users toUser = usersRepository.findById(toUserId).orElse(null);
        Users fromUser = usersRepository.findById(fromUserId).orElse(null);

        if(toUser == null || fromUser == null)
        {
            System.out.println("User not found");
            return false;
        }
        else if(!toUser.getInComingFriendRequestsList().contains(fromUser))
        {
            System.out.println("No friend request from" + fromUser.getName() + ".");
            return false;
        }
        else
        {
            fromUser.getFriendsList().add(toUser);
            fromUser.getOutGoingFriendRequestsList().remove(toUser);
            toUser.getInComingFriendRequestsList().remove(fromUser);
            return true;
        }
    }

    @GetMapping("/friends")
    public List<Users> getFriends(@RequestParam int userId) throws Exception
    {
        Users user = usersRepository.findById(userId).orElse(null);
        return user.getFriendsList();
    }
}
