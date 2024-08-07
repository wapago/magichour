package com.example.magichour.repository;

import com.example.magichour.entity.member.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
    Optional<UserEntity> findOneWithAuthoritiesByUserEmail(String userEmail);
}
