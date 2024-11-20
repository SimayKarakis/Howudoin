package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Messages;
import edu.sabanciuniv.howudoin.model.RequestString;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.MessagesRepository;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import edu.sabanciuniv.howudoin.service.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UsersController
{
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessagesRepository messagesRepository;

    public String getCurrentUserEmail()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // Assuming username is the email
        }
        return null;
    }

    //API Endpoint for registering user
    @PostMapping("/register")
    public boolean register(@RequestBody Users user) throws Exception // I'm returning boolean value here. In the security file it returns user object!!!
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersService.registerUser(user);
        System.out.println("User" + user.getName() + user.getLastName() + "is registered.");
        return true;
    }

    @GetMapping("/custom-date-search") // should I add this??
    public List<Users> searchCustomAccount(@RequestParam String startDate, @RequestParam String endDate) {
        return usersService.searchCustomUser(startDate, endDate);
    }

    @PostMapping("/friends/add")
    public boolean sendRequest(@RequestBody RequestString requestEmail)
    {
        String userEmail = getCurrentUserEmail();
        if (userEmail == null) {
            System.out.println("User not authenticated");
        }
        Users fromUser = usersRepository.findByEmail(userEmail);

        String toUserEmail = requestEmail.getRequestedString();
        Users toUser = usersRepository.findByEmail(toUserEmail);

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
        else if (fromUser.getOutGoingFriendRequestsList().contains(toUser.getId()) || toUser.getFriendsList().contains(fromUser.getId())) {
            System.out.println("Friend request already sent or you are already friends.");
            return false;
        }
        else
        {
            toUser.getInComingFriendRequestsList().add(fromUser.getId());
            fromUser.getOutGoingFriendRequestsList().add(toUser.getId());
            usersRepository.save(toUser);
            usersRepository.save(fromUser);
            System.out.println("Friend request sent to " + toUser.getName() + ".");
            return true;
        }
    }

    @PostMapping("/friends/accept")
    public boolean acceptRequest(@RequestBody RequestString receivedUserEmail) throws Exception
    {
        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail == null) {
            System.out.println("User not authenticated");
        }
        Users currentUser = usersRepository.findByEmail(currentUserEmail);

        String toUserEmail = receivedUserEmail.getRequestedString();
        Users receivedUser = usersRepository.findByEmail(toUserEmail);

        if(receivedUser == null || currentUser == null)
        {
            System.out.println("User not found");
            return false;
        }
        else if(!currentUser.getInComingFriendRequestsList().contains(receivedUser.getId()))
        {
            System.out.println("No friend request from " + currentUser.getName() + ".");
            return false;
        }
        else
        {
            currentUser.getFriendsList().add(receivedUser.getId());
            receivedUser.getFriendsList().add(currentUser.getId());

            currentUser.getInComingFriendRequestsList().remove(receivedUser.getId());
            receivedUser.getOutGoingFriendRequestsList().remove(currentUser.getId());

            usersRepository.save(currentUser);
            usersRepository.save(receivedUser);

            Messages conversation = new Messages();
            conversation.setUserID1(currentUser.getId());
            conversation.setUserID2(receivedUser.getId());
            conversation.setContent(Collections.emptyList());
            messagesRepository.save(conversation);

            System.out.println("Friend request from " + receivedUser.getName() + " is accepted.");
            return true;
        }
    }

    @GetMapping("/friends")
    public List<String> getFriends() throws Exception
    {
        String userEmail = getCurrentUserEmail();
        if (userEmail == null) {
            System.out.println("User not authenticated");
        }

        Users user = usersRepository.findByEmail(userEmail);
        return user.getFriendsList();
    }
}
