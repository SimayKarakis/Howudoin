package edu.sabanciuniv.howudoin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Messages
{
    @Id
    private Integer id;
    private Users from;
    private Users to; // what about sending a message to a group
    private List<String> content;
}
