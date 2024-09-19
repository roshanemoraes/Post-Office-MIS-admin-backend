package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserInfo, String> {
    Optional<UserInfo> findByEmail(String email);
}
