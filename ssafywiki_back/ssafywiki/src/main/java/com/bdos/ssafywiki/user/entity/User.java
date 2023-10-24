package com.bdos.ssafywiki.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.Temporal;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_nickname", nullable = false)
    private String nickname;

    @Column(name = "user_role", nullable = false)
    private String role;

    @Column(name = "user_number")
    private String number;

    @Column(name = "user_campus", nullable = false)
    private String campus;

    @CreationTimestamp
    @Column(name = "user_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "user_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

    @Setter
    @Column(name = "user_blocked_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime blockedAt;

    @Setter
    @Column
    private String refreshToken;

    @Builder
    public User(String email, String password, String name, String nickname, String role, String number, String campus, String refreshToken) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.number = number;
        this.campus = campus;
        this.refreshToken = refreshToken;
    }
}
