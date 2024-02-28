package com.example.demojwt.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demojwt.model.User;

public interface UserReposity extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
