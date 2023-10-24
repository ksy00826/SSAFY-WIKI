package com.bdos.ssafywiki.revision.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_content")
    private String content;

    @CreationTimestamp
    @Column(name = "comment_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "comment_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

}
