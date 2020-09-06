package com.velikokhatko.repository;

import com.velikokhatko.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByGoogleClientId(String googleClientId);
}
