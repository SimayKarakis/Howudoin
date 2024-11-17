package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Groups;
import edu.sabanciuniv.howudoin.model.Messages;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/groups/create")
    public boolean createGroup(@RequestBody Groups group)
    {
        groupsService.addGroup(group);
        return true;
    }

    @PostMapping("/groups/{id}/add-member")
    public boolean addMember(@PathVariable("id") int groupId, @RequestBody Users member)
    {
        groupsService.addMemberToGroup(groupId, member);
        return true;
    }

    @PostMapping("/groups/{id}/send")
    public boolean sendMessage(@PathVariable("id") int groupId, @RequestBody String message, @RequestBody Users member)
    {
        groupsService.sendMessageToGroup(groupId, member, message);
        return true;
    }

    @GetMapping("/groups/{id}/messages")
    public HashMap<Users,String> getMessages(@PathVariable("id") int groupId)
    {
        return groupsService.getGroupMessages(groupId);
    }

    @GetMapping("/groups/{id}/members")
    public List<Users> getMembers(@PathVariable("id") int groupId)
    {
        return groupsService.getGroupMembers(groupId);
    }
}
