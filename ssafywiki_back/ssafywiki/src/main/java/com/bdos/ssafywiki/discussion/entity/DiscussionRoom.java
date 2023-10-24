package com.bdos.ssafywiki.discussion.entity;

import com.bdos.ssafywiki.document.entity.Document;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "discussion_rooms")
public class DiscussionRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discussroom_id")
    private Long id;

    @CreatedDate
    @Column(name = "discussroom_created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "discussroom_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "discussroom_docs_id", nullable = false)
    private Document document;

    @Builder
    public DiscussionRoom(Document document) {
        this.document = document;
    }
}
