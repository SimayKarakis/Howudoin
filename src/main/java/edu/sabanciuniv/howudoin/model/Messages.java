package edu.sabanciuniv.howudoin.model;

import lombok.AllArgsConstructor;
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
    private List<MessageEntry> content;

    public Messages()
    {
        this.users = new ArrayList<>(2);
        this.content = new ArrayList<>();
    }

    @Data
    public static class MessageEntry{
        private String sender;
        private String messageContent;

        public MessageEntry(String sender, String messageContent) {
            this.sender = sender;
            this.messageContent = messageContent;
        }
    }
}
