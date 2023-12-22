package com.example.magichour.repository;

import com.example.magichour.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    boolean existsByUserId(String userId);
    void deleteByUserId(String userId);
}
