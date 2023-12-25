package com.example.magichour.entity.member;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Table(name = "t_refresh_token")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id", nullable = false)
    private Long refreshTokenId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "user_id", nullable = false)
    private String userId;

    private boolean expired;
}
