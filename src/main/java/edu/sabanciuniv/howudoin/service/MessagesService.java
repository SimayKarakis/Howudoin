package edu.sabanciuniv.howudoin.service;

import edu.sabanciuniv.howudoin.model.Messages;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.MessagesRepository;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessagesService
{
    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired
    private UsersRepository usersRepository;

    public void sendMessageToFriend(Messages message)
    {
        Optional<Users> fromUser = usersRepository.findById(message.getFrom().getId());
        Optional<Users> toUser = usersRepository.findById(message.getTo().getId());

        if(toUser.isPresent())
        {
            Users from = fromUser.get(); // WE WILL REMOVE THIS BUT HOW?
            Users friend = toUser.get();

            if(from == friend)
            {
                throw new RuntimeException("You cannot send a message to yourself.");
            }

            else
            {
                messagesRepository.save(message);
                System.out.println("Message is sent.");
            }
        }
        else
        {
            throw new RuntimeException("Friend not found.");
        }
    }
    // messages to individuals + messages to groups + messages recieved
    // OR
    // messages between two specific users ???
    // OR
    // messages sent
    // OR
    // messages sent and recieved

}
