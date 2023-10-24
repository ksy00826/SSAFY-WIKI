package com.bdos.ssafywiki.docs_category.entity;

import com.bdos.ssafywiki.category.entity.Category;
import com.bdos.ssafywiki.document.entity.Document;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Docs_category")
public class DocsCategory {
    @Id
    @GeneratedValue
    @Column(name = "docs_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_id")
    @Column(name = "docs_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Column(name = "category_id")
    private Category category;

    @CreationTimestamp
    @Column(name = "docs_category_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "redir_category_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

}
