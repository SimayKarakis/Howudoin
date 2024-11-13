package edu.sabanciuniv.howudoin.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Groups
{
    @Id
    private Integer id;
    private String name;
    private List<Users> groupMembers;
    private List<Messages> groupMessageList;
}
