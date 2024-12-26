package edu.sabanciuniv.howudoin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {
    private String senderId;  // User ID of the sender
    private String content;   // Message content
}
