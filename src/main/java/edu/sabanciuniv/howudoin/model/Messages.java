package edu.sabanciuniv.howudoin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Messages
{
    @Id
    private Integer id;
    private Users from;
    private Users to;
    private String content;
}
