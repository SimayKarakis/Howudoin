package edu.sabanciuniv.howudoin.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Users")
public class Users
{
    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private List<String> friendsList = new ArrayList<>();
    private List<String> inComingFriendRequestsList = new ArrayList<>();
    private List<String> outGoingFriendRequestsList = new ArrayList<>();
    private List<String> groupsList = new ArrayList<>();
    @CreatedDate
    private LocalDate createdDate;
}