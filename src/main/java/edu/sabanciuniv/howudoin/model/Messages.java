package edu.sabanciuniv.howudoin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Messages")
public class Messages
{
    @Id
    private String id;
    private List<String> users;
    private List<String> content;

    public Messages()
    {
        this.users = new ArrayList<>(2);
        this.content = new ArrayList<>();
    }
}
