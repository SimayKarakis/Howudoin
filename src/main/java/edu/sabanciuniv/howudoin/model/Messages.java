package edu.sabanciuniv.howudoin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Messages
{
    @Id
    private String id;
    private Users from; // we should make them emails or ids because if im sending a messsage to nese, I don't know her password
    private Users to; // what about sending a message to a group
    private List<String> content;
}
