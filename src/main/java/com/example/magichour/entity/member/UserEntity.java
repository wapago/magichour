package com.example.magichour.entity.member;

import com.example.magichour.entity.BaseEntity;
import com.example.magichour.enums.Authority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_email", length = 50, nullable = false)
    private String userEmail;

    @Column(name = "user_password", length = 100, nullable = false)
    private String userPassword;

    @Column(name = "user_name", length = 30, nullable = false)
    private String userName;

    @Column(name = "activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    private Authority authority;
}
