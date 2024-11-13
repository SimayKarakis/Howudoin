package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessagesRepository extends MongoRepository<Messages, Integer> {
}
