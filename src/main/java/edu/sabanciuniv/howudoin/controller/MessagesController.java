package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Messages;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessagesController
{
    @Autowired
    private MessagesService messagesService;

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
    public boolean sendMessage(@RequestBody Messages message) throws Exception
    {
        messagesService.sendMessageToFriend(message);
        return true;
    }

    /* I DON'T KNOW WHAT TO RETURN HERE

    @GetMapping("/messages")
    public List<> retrieveConversationHistory(@RequestBody Users user)
    {
        return messagesService.retrieveConversations(user);
    }
    
    */
}
