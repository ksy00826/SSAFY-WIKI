package com.bdos.ssafywiki.document.entity;

import com.bdos.ssafywiki.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Documents")
public class Document {
    @Id
    @GeneratedValue
    @Column(name = "docs_id")
    private Long id;

    //작성 유저 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "docs_title")
    private String title;
    @Column(name = "docs_is_redirect")
    private boolean isRedirect;
    @Column(name = "docs_is_deleted")
    private boolean isDeleted;

    @CreationTimestamp
    @Column(name = "docs_created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "docs_modified_at")
    private LocalDateTime modifiedAt;

    //self join
    @Column(name = "docs_parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_parent_id", referencedColumnName = "docs_id", insertable = false, updatable = false)
    private Document parent;
}
