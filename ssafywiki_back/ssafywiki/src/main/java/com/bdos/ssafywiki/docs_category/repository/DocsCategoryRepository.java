package com.bdos.ssafywiki.docs_category.repository;

import com.bdos.ssafywiki.docs_category.entity.DocsCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocsCategoryRepository extends JpaRepository<DocsCategory, Long> {

    @Query(value = "SELECT d.id FROM DocsCategory d WHERE d.document.id = :docsId")
    List<Long> findAllByDocsId(Long docsId);
}
