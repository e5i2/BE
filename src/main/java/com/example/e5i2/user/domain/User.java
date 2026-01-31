package com.example.e5i2.user.domain;

import com.example.e5i2.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderType gender;

    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Builder(access = AccessLevel.PRIVATE)
    private User(
            String email,
            String nickname,
            GenderType gender,
            Double height,
            Double weight
        ) {
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public static User createUser(
            String email,
            String nickname,
            GenderType gender,
            Double height,
            Double weight) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .gender(gender)
                .height(height)
                .weight(weight)
                .build();
    }
}
