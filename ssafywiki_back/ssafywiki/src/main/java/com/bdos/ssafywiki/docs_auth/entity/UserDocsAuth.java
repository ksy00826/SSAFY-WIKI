package com.bdos.ssafywiki.docs_auth.entity;

import com.bdos.ssafywiki.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name="User_docs_auth")
public class UserDocsAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_docs_auth_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_auth_id")
    private DocsAuth docsAuth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "docs_category_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Builder
    public UserDocsAuth(long id, DocsAuth docsAuth, User user) {
        this.id = id;
        this.docsAuth = docsAuth;
        this.user = user;
    }
}
