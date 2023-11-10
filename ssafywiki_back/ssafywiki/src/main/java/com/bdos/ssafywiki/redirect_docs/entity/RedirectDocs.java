package com.bdos.ssafywiki.redirect_docs.entity;

import com.bdos.ssafywiki.document.entity.Document;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "redirect_keyword")
public class RedirectDocs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "redir_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "redir_docs_id")
    private Document redirectDocs;

    @Column(name = "redir_keyword")
    private String keyword;

    @CreationTimestamp
    @Column(name = "redir_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "redir_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

    @Builder
    public RedirectDocs(String keyword) {
        this.keyword = keyword;
    }

    public void setRedirectDocs(Document redirectDocs) {
        this.redirectDocs = redirectDocs;
    }

    public RedirectDocs() {
    }
}
