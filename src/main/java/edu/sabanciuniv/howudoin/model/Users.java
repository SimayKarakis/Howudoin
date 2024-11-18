package edu.sabanciuniv.howudoin.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
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
    private List<Users> friendsList;
    private List<Users> inComingFriendRequestsList;
    private List<Users> outGoingFriendRequestsList;
    @CreatedDate
    private LocalDate createdDate;
}