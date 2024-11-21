package edu.sabanciuniv.howudoin.service;

import edu.sabanciuniv.howudoin.model.Messages;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.MessagesRepository;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MessagesService
{
    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired
    private UsersRepository usersRepository;

    public void sendMessageToFriend(Users user1, Users user2, String content)
    {
        if (user2 != null)
        {
            if (user1.getFriendsList().contains(user2.getId()))
            {
                if (user1 != user2)
                {
                    List<Messages> conversation = messagesRepository.findAll();

                    for (Messages message : conversation)
                    {
                        if(message.getUsers().contains(user1.getEmail()) && message.getUsers().contains(user2.getEmail()))
                        {
                            message.getContent().add(content);
                            messagesRepository.save(message);
                            break;
                        }
                    }

                    System.out.println("Message is sent.");
                }
                else
                {
                    throw new RuntimeException("You cannot send a message to yourself.");
                }
            }
            else
            {
                throw new RuntimeException("Friend not found.");
            }
        }
        else
        {
            throw new RuntimeException("User not found.");
        }
    }
    public HashMap<String, String> retrieveConversations(Users user1) {
        List<Messages> allMessages = messagesRepository.findAll();
        HashMap<String, String> conversationMap = new HashMap<>();

        for (Messages message : allMessages) {
            if (message.getUsers().contains(user1.getEmail())) {
                List<String> users = message.getUsers();
                List<String> contents = message.getContent();

                // Associate each message in content with the corresponding user
                for (int i = 0; i < contents.size(); i++) {
                    String senderEmail = users.get(i % users.size()); // Alternate between users if needed
                    conversationMap.put(senderEmail, contents.get(i));
                }
            }
        }

        return conversationMap;
    }

}