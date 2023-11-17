package com.bdos.ssafywiki.redirect_docs.repository;

import com.bdos.ssafywiki.redirect_docs.entity.RedirectDocs;
import com.bdos.ssafywiki.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RedirectDocsRepository extends JpaRepository<RedirectDocs, Long> {
    @Query(value = "SELECT r FROM RedirectDocs r WHERE r.redirectDocs.id = :docsId")
    RedirectDocs findByDocsId(Long docsId);
}
