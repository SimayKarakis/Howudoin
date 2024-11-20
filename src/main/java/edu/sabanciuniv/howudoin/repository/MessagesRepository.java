package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessagesRepository extends MongoRepository<Messages, String> {
    Messages findByUserID1AndUserID2(String fromUserID, String toUserID);
}
