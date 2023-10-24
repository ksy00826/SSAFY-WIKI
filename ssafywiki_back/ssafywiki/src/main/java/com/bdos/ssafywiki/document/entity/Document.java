package com.bdos.ssafywiki.document.entity;

import com.bdos.ssafywiki.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "docs_user_id") //?
    private User user;

    //self join ?
//    @Column(name = "docs_parent_id")
//    private Long parentId;

    //자식에서 부모를 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_id", referencedColumnName = "docs_parent_id")
    private Document parent;

    //부모에서 자식을 참조
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Document> children;

    @Column(name = "docs_title")
    private String title;
    @Column(name = "docs_is_redirect")
    private boolean redirect;
    @Column(name = "docs_is_deleted")
    private boolean deleted;

    @CreationTimestamp
    @Column(name = "docs_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "docs_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;
}
