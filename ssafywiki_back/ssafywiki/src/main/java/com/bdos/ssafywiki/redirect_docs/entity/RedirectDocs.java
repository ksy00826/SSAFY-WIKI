package com.bdos.ssafywiki.redirect_docs.entity;

import com.bdos.ssafywiki.document.entity.Document;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "redirect_docs")
public class RedirectDocs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "redir_docs_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_docs_id")
    private Document originalDocs;

    @Column(name = "redir_title")
    private String title;

    @CreationTimestamp
    @Column(name = "redir_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "redir_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

}
