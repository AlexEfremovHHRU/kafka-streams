package com.example.kafkastream.repository;

import com.example.kafkastream.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
