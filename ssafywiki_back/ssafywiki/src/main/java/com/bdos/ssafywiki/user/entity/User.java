package com.bdos.ssafywiki.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name = "user_role")
    private String role;

    @Column(name = "user_number")
    private String number;

    @Column(name = "user_campus")
    private String campus;

    @CreationTimestamp
    @Column(name = "user_created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "user_modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "user_blocked_at")
    private Timestamp blockedAt;

    @Column
    private String refreshToken;
}
