package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface UsersRepository extends MongoRepository<Users, Integer>
{
    Users findByEmail(String email);
    List<Users> findByCreatedDate(LocalDate start, LocalDate end);
}
