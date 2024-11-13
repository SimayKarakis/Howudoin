package edu.sabanciuniv.howudoin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Users")
public class Users
{
    @Id
    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private List<Users> friendsList;
}

// I did not add createdDate here because it is not written in the document,
// should we add it?