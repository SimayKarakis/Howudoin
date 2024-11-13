package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users, Integer> {
}
