package com.bdos.ssafywiki.discussion.entity;

import com.bdos.ssafywiki.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "discussions")
public class Discussion {
    @Id
    @Column(name = "discussion_id")
    private Long id;

    @Column(name = "discussion_content")
    private String content;

    @CreationTimestamp
    @Column(name = "discussion_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "discussion_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_discussroom_id")
    private DiscussionRoom discussionRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_user_id")
    private User user;

    @Builder
    public Discussion(String content, DiscussionRoom discussionRoom, User user) {
        this.content = content;
        this.discussionRoom = discussionRoom;
        this.user = user;
    }
}
