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

    public void sendMessageToFriend(Users user1, String user2ID, String messageContent)
    {
        Optional<Users> toUser = usersRepository.findById(user2ID);
        if (toUser.isPresent())
        {
            Users friend = toUser.get();

            if (user1.getFriendsList().contains(user2ID))
            {

                if (user1 == friend)
                {
                    throw new RuntimeException("You cannot send a message to yourself.");
                }
                else
                {
                    Messages conversation = messagesRepository.findByUserID1AndUserID2(user1.getId(), user2ID);
                    if(conversation == null)
                    {
                        conversation = messagesRepository.findByUserID1AndUserID2(user2ID, user1.getId());
                        if(conversation == null)
                        {
                            System.out.println("Conversation not found");
                        }
                        else
                        {
                            conversation.getContent().add(messageContent);
                            messagesRepository.save(conversation);
                        }
                    }
                    else
                    {
                        conversation.getContent().add(messageContent);
                        messagesRepository.save(conversation);
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
        Messages conversation = messagesRepository.findByUserID1AndUserID2(fromUser.getId(), toUser.getId());
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
                return conversation.getContent();
            }
        }
        else
        {
            return conversation.getContent();
        }
    }
}