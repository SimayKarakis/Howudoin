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

import java.util.*;

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
    public boolean register(@RequestBody Users user) throws Exception
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersService.registerUser(user);
        System.out.println("User " + user.getName() + " " + user.getLastName() + " is registered.");
        return true;
    }

    @GetMapping("/custom-date-search")
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

        String toUserEmail = requestEmail.getS();
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

    @GetMapping("/friends/incoming")
    public List<String> getFriendRequests() throws Exception
    {
        String userEmail = getCurrentUserEmail();
        if (userEmail == null) {
            System.out.println("User not authenticated");
        }
        Users user = usersRepository.findByEmail(userEmail);

        List<String> friendNames = new ArrayList<>();
        if(user.getInComingFriendRequestsList().isEmpty())
        {
            System.out.println("No friend requests found");
            return null;
        }

        for(String ids : user.getInComingFriendRequestsList()){
            Users friend = usersRepository.findById(ids).orElse(null);
            friendNames.add(friend.getName() + " " + friend.getLastName());
        }
        return friendNames;
    }

    @PostMapping("/friends/accept")
    public boolean acceptRequest(@RequestBody RequestString requestName) throws Exception
    {
        String currentUserEmail = getCurrentUserEmail();
        if (currentUserEmail == null) {
            System.out.println("User not authenticated");
        }
        Users currentUser = usersRepository.findByEmail(currentUserEmail);

        String name = requestName.getS();
        String userName = name.substring(0, name.indexOf(' '));
        Users receivedUser = usersRepository.findByName(userName);

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
            conversation.getUsers().add(currentUser.getEmail());
            conversation.getUsers().add(receivedUser.getEmail());
            //conversation.setContent(new List<Messages.MessageEntry>());
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

        List<String> friendNames = new ArrayList<>();
        for(String ids : user.getFriendsList()){
            Users friend = usersRepository.findById(ids).orElse(null);
            friendNames.add(friend.getName() + " " + friend.getLastName());
        }
        return friendNames;
    }
}