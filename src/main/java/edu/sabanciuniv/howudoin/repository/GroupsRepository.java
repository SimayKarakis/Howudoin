package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.Groups;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupsRepository extends MongoRepository<Groups, Integer> {
}
