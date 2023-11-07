package com.bdos.ssafywiki.redirect_docs.repository;

import com.bdos.ssafywiki.redirect_docs.entity.RedirectDocs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedirectDocsRepository extends JpaRepository<RedirectDocs, Long> {
}
