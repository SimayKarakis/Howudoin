package edu.sabanciuniv.howudoin.service;

import edu.sabanciuniv.howudoin.model.Groups;
import edu.sabanciuniv.howudoin.model.Messages;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.MessagesRepository;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

                if (user1 == user2)
                {
                    throw new RuntimeException("You cannot send a message to yourself.");
                }
                else
                {
                    List<Messages> conversation = messagesRepository.findByUserID1AndUserID2(user1.getId(), user2.getId());
                    if(conversation.isEmpty())
                    {
                        conversation = messagesRepository.findByUserID1AndUserID2(user2.getId(), user1.getId());
                        if(conversation.isEmpty())
                        {
                            System.out.println("Conversation not found");
                        }
                        else
                        {
                            for (Messages message : conversation)
                            {
                                message.getContent().add(content);
                                messagesRepository.save(message);
                            }
                        }
                    }
                    else
                    {
                        for (Messages message : conversation)
                        {
                            message.getContent().add(content);
                            messagesRepository.save(message);
                        }
                    }
                    System.out.println("Message is sent.");
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
    public List<String> retrieveConversations(Users fromUser, Users toUser)
    {
        List<Messages> conversation = messagesRepository.findByUserID1AndUserID2(fromUser.getId(), toUser.getId());
        if(conversation == null)
        {
            conversation = messagesRepository.findByUserID1AndUserID2(toUser.getId(), fromUser.getId());
            if(conversation == null)
            {
                System.out.println("Conversation not found");
                return null;
            }
            else
            {
                for (Messages message : conversation)
                {
                    return message.getContent();
                }
            }
        }
        else
        {
            for (Messages message : conversation)
            {
                return message.getContent();
            }
        }
        return List.of();
    }
}