package edu.sabanciuniv.howudoin.service;

import edu.sabanciuniv.howudoin.model.Groups;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class GroupsService
{
    @Autowired
    private GroupsRepository groupsRepository;

    public void addGroup(Groups group)
    {
        groupsRepository.save(group);
        System.out.println("Group '" + group.getName() + "' is created.");
    }

    public void addMemberToGroup(String groupId, Users member)
    {
        Optional<Groups> wantedGroup = groupsRepository.findById(groupId);

        if(wantedGroup.isPresent())
        {
            Groups group = wantedGroup.get();
            group.getGroupMemberList().add(member.getId());
            groupsRepository.save(group);
            System.out.println("Member '" + member.getName() + "' is added to group '" + group.getName() + "'.");
        }
        else
        {
            throw new RuntimeException("Group with ID " + groupId + " does not exist.");
        }
    }

    public void sendMessageToGroup(String groupId, String memberID, String message)
    {
        Optional<Groups> wantedGroup = groupsRepository.findById(groupId);

        if(wantedGroup.isPresent())
        {
            Groups group = wantedGroup.get();
            group.getGroupMessageMap().put(memberID, message);
            groupsRepository.save(group);
            System.out.println("Message is sent to group '" + group.getName() + "'.");
        }
        else
        {
            throw new RuntimeException("Group with ID " + groupId + " does not exist.");
        }
    }

    public HashMap<String,String> getGroupMessages(String groupId)
    {
        Optional<Groups> wantedGroup = groupsRepository.findById(groupId);

        if(wantedGroup.isPresent())
        {
            Groups group = wantedGroup.get();
            return group.getGroupMessageMap();

        }
        else
        {
            throw new RuntimeException("Group with ID " + groupId + " does not exist.");
        }
    }

    public List<String> getGroupMembers(String groupId)
    {
        Optional<Groups> wantedGroup = groupsRepository.findById(groupId);

        if(wantedGroup.isPresent())
        {
            Groups group = wantedGroup.get();
            return group.getGroupMemberList();

        }
        else
        {
            throw new RuntimeException("Group with ID " + groupId + " does not exist.");
        }
    }
}
