package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.RequestMessage;
import edu.sabanciuniv.howudoin.model.RequestString;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import edu.sabanciuniv.howudoin.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
public class MessagesController
{
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private UsersRepository usersRepository;

    /*
        -	POST /messages/send: Send a message to a friend
        -	GET /messages: Retrieve conversation history
    */

    public String getCurrentUserEmail()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // Assuming username is the email
        }
        return null;
    }

    @PostMapping("/messages/send")
    public boolean sendMessage(@RequestBody RequestMessage message) throws Exception
    {
        String fromUserEmail = getCurrentUserEmail();
        if (fromUserEmail == null) {
            System.out.println("User not authenticated");
            return false;
        }
        Users fromUser = usersRepository.findByEmail(fromUserEmail);

        String toUserEmail = message.getToUserEmail();
        Users toUser = usersRepository.findByEmail(toUserEmail);

        String content = message.getContent();
        messagesService.sendMessageToFriend(fromUser, toUser, content);
        return true;
    }

    @GetMapping("/messages")
    public LinkedHashMap<String, String> retrieveConversationHistory(@RequestBody RequestString requestEmail) {
        String fromUserEmail = getCurrentUserEmail();
        if (fromUserEmail == null) {
            throw new RuntimeException("User not authenticated");
        }
        Users fromUser = usersRepository.findByEmail(fromUserEmail);

        String email = requestEmail.getS();
        Users friend = usersRepository.findByEmail(email);

        if(friend == null) {
            throw new RuntimeException("Friend not found");
        }

        return messagesService.retrieveConversations(fromUser, friend);
    }

}