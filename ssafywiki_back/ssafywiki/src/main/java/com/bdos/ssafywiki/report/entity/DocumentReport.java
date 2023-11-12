package com.bdos.ssafywiki.report.entity;

import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.report.enums.Status;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@Table(name = "document_reports")
public class DocumentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docs_report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_report_docs_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_report_user_id")
    private User user;

    @CreatedDate
    @Column(name = "docs_report_created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "docs_report_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

    @Setter
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'Processing'")
    @Column(name = "docs_report_status")
    private Status status;

    @Builder
    public DocumentReport(Document document, User user) {
        this.document = document;
        this.user = user;
    }

    @Builder
    public DocumentReport(Status status) {
        this.status = status;
    }
}
