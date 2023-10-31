package com.bdos.ssafywiki.docs_category.repository;

import com.bdos.ssafywiki.docs_category.entity.DocsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocsCategoryRepository extends JpaRepository<DocsCategory, Long> {
}
