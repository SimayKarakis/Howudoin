package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Groups;
import edu.sabanciuniv.howudoin.model.Message;
import edu.sabanciuniv.howudoin.model.RequestString;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.UsersRepository;
import edu.sabanciuniv.howudoin.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class GroupsController {

    @Autowired
    private GroupsService groupsService;
    @Autowired
    private UsersRepository usersRepository;

    /*
     * +	POST /groups/create: Creates a new group with a given name and members.
     * +	POST /groups/:groupId/add-member: Adds a new member to an existing group.
     * -	POST /groups/:groupId/send: Sends a message to all members of the specified group.
     * -	GET /groups/:groupId/messages: Retrieves the message history for the specified group.
     * -	GET /groups/:groupId/members: Retrieves the list of members for the specified group.
     */

    // Utility method to get the current user's email
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // Assuming username is the email
        }
        return null;
    }

    @PostMapping("/groups/create")
    public boolean createGroup(@RequestBody Groups group)
    {
        String currentUserEmail = getCurrentUserEmail();
        Users currentUser = usersRepository.findByEmail(currentUserEmail);
        if (currentUser == null) {
            System.out.println("User not authenticated");
            return false;
        }
        group.getGroupMemberList().add(currentUserEmail);

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Istanbul"));
        group.setCreationTime(zonedDateTime.toLocalDateTime());

        groupsService.addGroup(group);

        usersRepository.save(currentUser);
        return true;
    }

    // Add a member to an existing group
    @PostMapping("/groups/{id}/add-member")
    public boolean addMember(@PathVariable("id") String groupId, @RequestBody RequestString memberEmail) {
        String toUserEmail = memberEmail.getRequestedString();
        Users toUser = usersRepository.findByEmail(toUserEmail);

        groupsService.addMemberToGroup(groupId, toUser);
        return true;
    }

    // Send a message to a group
    @PostMapping("/groups/{id}/send")
    public boolean sendMessage(@PathVariable("id") String groupId, @RequestBody RequestString requestedMessage) {
        String userEmail = getCurrentUserEmail();
        if (userEmail == null) {
            System.out.println("User not authenticated");
        }

        Users fromUser = usersRepository.findByEmail(userEmail);
        String messageContent = requestedMessage.getRequestedString();

        // Create a new Message object
        Message newMessage = new Message(fromUser.getId(), messageContent);

        groupsService.sendMessageToGroup(groupId, newMessage);  // Pass Message object to service
        return true;
    }

    @GetMapping("/groups/{id}/messages")
    public List<Message> getMessages(@PathVariable("id") String groupId) {
        List<Message> messages = groupsService.getGroupMessages(groupId);

        // Replace senderId with email for each message
        for (Message message : messages) {
            Users user = usersRepository.findById(message.getSenderId()).orElse(null);
            if (user != null) {
                // Replace senderId with email address
                message.setSenderId(user.getEmail());  // Set the senderId to the user's email
            } else {
                message.setSenderId("Unknown User");  // If no user found, set a default message
            }
        }

        return messages;
    }

    @GetMapping("/groups/{id}/created-at")
    public ResponseEntity<LocalDateTime> getGroupCreatedAt(@PathVariable("id") String groupId) {
        try {
            LocalDateTime creationTime = groupsService.getGroupCreationTime(groupId);
            System.out.println("Retrieved creation time: " + creationTime); // Add logging
            return ResponseEntity.ok(creationTime);
        } catch (Exception e) {
            e.printStackTrace(); // This will give us the full stack trace
            throw e;
        }
    }

    // Get the list of members for a group
    @GetMapping("/groups/{id}/members")
    public List<String> getMembers(@PathVariable("id") String groupId) {
        return groupsService.getGroupMembers(groupId);
    }

    // Get all groups for a user
    @GetMapping("/groups")
    public ResponseEntity<?> getUserGroups(@RequestHeader("Authorization") String token) {
        // Extract the user's email from the token
        String userEmail = getCurrentUserEmail();

        try {
            // Fetch groups for the user
            List<Groups> userGroups = groupsService.getGroupsForUser(userEmail);
            return ResponseEntity.ok(userGroups);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }



}
