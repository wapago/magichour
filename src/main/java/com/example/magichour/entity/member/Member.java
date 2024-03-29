package com.example.magichour.entity.member;

import com.example.magichour.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", length = 50, nullable = false)
    private String userId;

    @Column(name = "user_password", length = 100, nullable = false)
    private String userPassword;

    @Column(name = "user_name", length = 30, nullable = false)
    private String userName;

    @Column(name = "activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    private Authority authority;
}
