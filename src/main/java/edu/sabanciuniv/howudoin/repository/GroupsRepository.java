package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.Groups;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface GroupsRepository extends MongoRepository<Groups, String> {
    List<Groups> findAllByGroupMemberListContaining(String memberEmail);
}
