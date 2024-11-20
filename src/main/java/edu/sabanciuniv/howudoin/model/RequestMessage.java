package edu.sabanciuniv.howudoin.model;

import lombok.Data;

@Data
public class RequestMessage
{
    private String toUserEmail;
    private String content;
}
