package com.bdos.ssafywiki.docs_auth.entity;

import com.bdos.ssafywiki.document.entity.Document;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name="Docs_auth")
@SequenceGenerator(
        name = "DOCS_AUTH_SEQ_GENERATOR"
        , sequenceName = "DOCS_AUTH_SEQ"
        , initialValue = 1000
        , allocationSize = 1
)
public class DocsAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "DOCS_AUTH_SEQ_GENERATOR")
    @Column(name="docs_auth_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_auth_docs_id")
    private Document document;

    @CreationTimestamp
    @Column(name = "docs_auth_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "docs_auth_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

    @Builder
    public DocsAuth(Document document) {
        this.document = document;
    }
}
