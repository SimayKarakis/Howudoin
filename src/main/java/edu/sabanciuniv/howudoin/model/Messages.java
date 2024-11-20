package edu.sabanciuniv.howudoin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "Messages")
public class Messages
{
    @Id
    private String id;
    private String UserID1;
    private String UserID2;
    private List<String> content;
    // This hashmap element's has the key 1 if the message is coming from user 1 to user 2
    // and 2 if the message is coming from user 2 to user 1.
}
