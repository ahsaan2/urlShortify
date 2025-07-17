package com.url.shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.url.shortener.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
Optional<User> findByUsername(String username);
//boolean existsByUsername(String username);
}

