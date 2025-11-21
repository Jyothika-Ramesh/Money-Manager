package com.org.familyexpenseapp.repository;

import com.org.familyexpenseapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByMail(String mail);
    List<User> findByFamilyname(String familyname);
    boolean existsByMail(String mail);
}

