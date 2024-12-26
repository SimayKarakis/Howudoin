package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Messages;
import edu.sabanciuniv.howudoin.model.RequestMessage;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import edu.sabanciuniv.howudoin.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

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

        String nameLastname = message.getS();
        String userName = nameLastname.substring(0, nameLastname.indexOf(' '));
        Users toUser = usersRepository.findByName(userName);

        String content = message.getContent();
        messagesService.sendMessageToFriend(fromUser, toUser, content);
        return true;
    }

    @GetMapping("/messages")
    public List<Messages.MessageEntry> retrieveConversationHistory(@RequestParam String requestNameLastname) throws Exception {
        String fromUserEmail = getCurrentUserEmail();
        if (fromUserEmail == null) {
            throw new RuntimeException("User not authenticated");
        }
        Users fromUser = usersRepository.findByEmail(fromUserEmail);

        String userName = requestNameLastname.substring(0, requestNameLastname.indexOf(' '));
        Users friend = usersRepository.findByName(userName);

        if(friend == null) {
            throw new RuntimeException("Friend not found");
        }

        return messagesService.retrieveConversations(fromUser, friend);
    }

}