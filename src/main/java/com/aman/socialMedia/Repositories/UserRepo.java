package com.aman.socialMedia.Repositories;

import com.aman.socialMedia.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer>{

    Optional<User> findByEmail(String username);                     //It is used to represent a value that may or may not be present. In other words, an Optional object can either contain a non-null value (in which case it is considered present) or it can contain no value at all (in which case it is considered empty)
}
