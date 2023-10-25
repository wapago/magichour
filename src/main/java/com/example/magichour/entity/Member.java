package com.example.magichour.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String userId;

    @Column(length = 30, nullable = false)
    private String userPassword;

    @Column(length = 30, nullable = false)
    private String userName;
}
