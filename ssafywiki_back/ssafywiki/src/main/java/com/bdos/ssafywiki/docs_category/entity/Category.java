package com.bdos.ssafywiki.docs_category.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long id;

    @Column(name = "category_name")
    private String name;

    @CreationTimestamp
    @Column(name = "category_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "category_modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;
}
