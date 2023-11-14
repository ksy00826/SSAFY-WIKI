package com.bdos.ssafywiki.document.repository;

import com.bdos.ssafywiki.document.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findTop10ByOrderByModifiedAtDesc();
    Page<Document> findAllBy(PageRequest pageRequest);
}
