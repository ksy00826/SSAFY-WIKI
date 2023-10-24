package com.bdos.ssafywiki.template.entity;

import com.bdos.ssafywiki.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.service.annotation.GetExchange;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long id;

    @Column(name = "template_title")
    private String title;

    @Column(name = "template_content")
    private String content;

    @Column(name = "template_is_secret")
    private boolean secret;

    @CreationTimestamp
    @Column(name = "template_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "template_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_user_id")
    private User user;
}
