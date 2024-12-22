package edu.sabanciuniv.howudoin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "Groups")
public class Groups
{
    @Id
    private String id;
    private String name;
    private List<String> groupMemberList = new ArrayList<>(); //stores user ID's
    private HashMap<String,String> groupMessageMap = new HashMap<>(); //stores user ID's and content
    @CreatedDate
    private LocalDate createdDate;
}
