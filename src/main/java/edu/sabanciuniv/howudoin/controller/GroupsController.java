package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Groups;
import edu.sabanciuniv.howudoin.model.Messages;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class GroupsController
{
    @Autowired
    private GroupsService groupsService;

    /*
     * +	POST /groups/create: Creates a new group with a given name and members.
     * +	POST /groups/:groupId/add-member: Adds a new member to an existing group.
     * -	POST /groups/:groupId/send: Sends a message to all members of the specified group.
     * -	GET /groups/:groupId/messages: Retrieves the message history for the specified group.
     * -	GET /groups/:groupId/members: Retrieves the list of members for the specified group.
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

    @PostMapping("/groups/create")
    public boolean createGroup(@RequestBody Groups group)
    {
        groupsService.addGroup(group);
        return true;
    }

    @PostMapping("/groups/{id}/add-member")
    public boolean addMember(@PathVariable("id") String groupId, @RequestBody Users member)
    {
        groupsService.addMemberToGroup(groupId, member);
        return true;
    }

    @PostMapping("/groups/{id}/send")
    public boolean sendMessage(@PathVariable("id") String groupId, @RequestBody String message, @RequestBody Users member)
    {
        groupsService.sendMessageToGroup(groupId, member, message);
        return true;
    }

    @GetMapping("/groups/{id}/messages")
    public HashMap<Users,String> getMessages(@PathVariable("id") String groupId)
    {
        return groupsService.getGroupMessages(groupId);
    }

    @GetMapping("/groups/{id}/members")
    public List<Users> getMembers(@PathVariable("id") String groupId)
    {
        return groupsService.getGroupMembers(groupId);
    }
}
