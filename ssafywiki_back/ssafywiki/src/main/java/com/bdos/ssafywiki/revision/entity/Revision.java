package com.bdos.ssafywiki.revision.entity;

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

    @CreationTimestamp
    @Column(name="rev_created_at",  columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "rev_docs_id")
//    private Document document;

    @Builder
    public Revision(Long diffAmount, Long number, Revision parent, Revision origin, Comment comment, Content content) {
        this.diffAmount = diffAmount;
        this.number = number;
        this.parent = parent;
        this.origin = origin;
        this.comment = comment;
        this.content = content;
    }
}
