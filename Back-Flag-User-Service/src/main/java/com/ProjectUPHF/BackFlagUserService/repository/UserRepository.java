package com.ProjectUPHF.BackFlagUserService.repository;

import com.ProjectUPHF.BackFlagUserService.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
    List<User> findTop10ByOrderByScoreDesc();
}
