package edu.sabanciuniv.howudoin.service;

import edu.sabanciuniv.howudoin.model.Groups;
import edu.sabanciuniv.howudoin.model.Message;
import edu.sabanciuniv.howudoin.model.Users;
import edu.sabanciuniv.howudoin.repository.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GroupsService {

    @Autowired
    private GroupsRepository groupsRepository;

    public void addGroup(Groups group) {
        groupsRepository.save(group);
        System.out.println("Group '" + group.getName() + "' is created at " + group.getCreationTime());
    }

    public void addMemberToGroup(String groupId, Users member) {
        Optional<Groups> wantedGroup = groupsRepository.findById(groupId);

        if (wantedGroup.isPresent()) {
            Groups group = wantedGroup.get();
            group.getGroupMemberList().add(member.getEmail());
            groupsRepository.save(group);
            System.out.println("Member '" + member.getName() + "' is added to group '" + group.getName() + "'.");
        } else {
            throw new RuntimeException("Group with ID " + groupId + " does not exist.");
        }
    }

    // Send a message to a group
    public void sendMessageToGroup(String groupId, Message message) {
        Optional<Groups> wantedGroup = groupsRepository.findById(groupId);

        if (wantedGroup.isPresent()) {
            Groups group = wantedGroup.get();
            // Add the message to the group's message list
            group.getGroupMessageList().add(message);
            groupsRepository.save(group);
            System.out.println("Message from '" + message.getSenderId() + "' is sent to group '" + group.getName() + "'.");
        } else {
            throw new RuntimeException("Group with ID " + groupId + " does not exist.");
        }
    }

    // Get all messages from a group
    public List<Message> getGroupMessages(String groupId) {
        Optional<Groups> wantedGroup = groupsRepository.findById(groupId);

        if (wantedGroup.isPresent()) {
            Groups group = wantedGroup.get();
            return group.getGroupMessageList(); // Return the list of messages
        } else {
            throw new RuntimeException("Group with ID " + groupId + " does not exist.");
        }
    }

    // Get all members of a group
    public List<String> getGroupMembers(String groupId) {
        Optional<Groups> wantedGroup = groupsRepository.findById(groupId);

        if (wantedGroup.isPresent()) {
            Groups group = wantedGroup.get();
            return group.getGroupMemberList(); // Return the list of member emails
        } else {
            throw new RuntimeException("Group with ID " + groupId + " does not exist.");
        }
    }

    // Get all groups a user is a part of
    public List<Groups> getGroupsForUser(String userEmail) {
        return groupsRepository.findAllByGroupMemberListContaining(userEmail); // Query groups by member email
    }

    public LocalDateTime getGroupCreationTime(String groupId) {
        Optional<Groups> wantedGroup = groupsRepository.findById(groupId);

        if (wantedGroup.isPresent()) {
            Groups group = wantedGroup.get();
            if (group.getCreationTime() == null) {
                throw new RuntimeException("Creation time not set for group: " + groupId);
            }
            return group.getCreationTime();
        } else {
            throw new RuntimeException("Group with ID " + groupId + " does not exist.");
        }
    }
}
