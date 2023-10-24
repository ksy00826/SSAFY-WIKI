package com.bdos.ssafywiki.document.entity;

import com.bdos.ssafywiki.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "Documents")
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docs_id")
    private Long id;

    //작성 유저 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_user_id")
    private User user;

    //self join
    //자식에서 부모를 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_parent_id")
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

    public Document(String title) {
        this.title = title;
    }

    //연관관계 설정
    public void setUser(User user){
        this.user = user;
    }

    public void setParent(Document parent) {
        this.parent = parent;
    }

    public void setChildren(List<Document> children) {
        this.children = children;
    }
}
