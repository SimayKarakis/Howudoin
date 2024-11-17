package edu.sabanciuniv.howudoin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Groups
{
    @Id
    private Integer id;
    private String name;
    private List<Users> groupMemberList;
    private HashMap<Users,String> groupMessageMap;
}
