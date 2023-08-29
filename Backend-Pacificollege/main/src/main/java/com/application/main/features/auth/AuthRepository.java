package com.application.main.features.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    Optional<Auth> findByUsername(String usename);
}
