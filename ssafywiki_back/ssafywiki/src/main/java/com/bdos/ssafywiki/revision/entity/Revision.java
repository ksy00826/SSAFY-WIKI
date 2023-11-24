package com.bdos.ssafywiki.revision.entity;

import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "revisions")
public class Revision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rev_id")
    private Long id;

    @CreatedDate
    @Column(name="rev_created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="rev_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

    @Column(name="rev_diff_amount", nullable = false)
    private Long diffAmount;

    @Column(name="rev_number", nullable = false)
    private Long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rev_parent_id")
    private Revision parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rev_origin_id")
    private Revision origin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rev_comment_id")
    private Comment comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rev_content_id")
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "rev_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rev_docs_id")
    private Document document;

    @Builder
    public Revision(Long diffAmount, Long number, Revision parent, Revision origin, Comment comment, Content content, User user, Document document) {
        this.diffAmount = diffAmount;
        this.number = number;
        this.parent = parent;
        this.origin = origin;
        this.comment = comment;
        this.content = content;
        this.user = user;
        this.document = document;
    }

    public Revision(Long number, Comment comment, Content content, User user, Document document) {
        this.number = number;
        this.comment = comment;
        this.content = content;
        this.user = user;
        this.document = document;
    }

    public Revision(Long diffAmount, Long number) {
        this.diffAmount = diffAmount;
        this.number = number;
    }

    public void setParent(Revision parent) {
        this.parent = parent;
    }

    public void setOrigin(Revision origin) {
        this.origin = origin;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
