package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface UsersRepository extends MongoRepository<Users, String>
{
    Users findByEmail(String email);
    Users findByName(String name);
    List<Users> findByCreatedDate(LocalDate start, LocalDate end);
}
