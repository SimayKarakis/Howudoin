package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Messages;
import edu.sabanciuniv.howudoin.model.RequestString;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
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
import java.util.Optional;

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
    public boolean sendMessage(@RequestBody RequestString toUserID, @RequestBody RequestString messageContent) throws Exception
    {
        String userEmail = getCurrentUserEmail();
        if (userEmail == null) {
            System.out.println("User not authenticated");
            return false;
        }
        Users fromUser = usersRepository.findByEmail(userEmail);

        String toUserID2 = toUserID.getRequestedString();
        String content = messageContent.getRequestedString();
        messagesService.sendMessageToFriend(fromUser, toUserID2, content);
        return true;
    }

    @GetMapping("/messages")
    public List<String> retrieveConversationHistory(@RequestBody RequestString requestedToUserEmail)
    {
        String fromUserEmail = getCurrentUserEmail();
        if (fromUserEmail == null)
        {
            System.out.println("User not authenticated");
            return null;
        }
        Users fromUser = usersRepository.findByEmail(fromUserEmail);

        String toUserEmail = requestedToUserEmail.getRequestedString();
        Users toUser = usersRepository.findByEmail(toUserEmail);

        return messagesService.retrieveConversations(fromUser, toUser);
    }

}
