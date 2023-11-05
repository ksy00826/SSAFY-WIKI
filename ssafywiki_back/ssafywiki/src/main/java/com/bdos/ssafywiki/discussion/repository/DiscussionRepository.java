package com.bdos.ssafywiki.discussion.repository;


import com.bdos.ssafywiki.discussion.entity.Discussion;
import com.bdos.ssafywiki.revision.entity.Revision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findTop100ByDocumentIdOrderByCreatedAtAsc(Long docsId);

    Discussion findTopByDocumentIdOrderByCreatedAtDesc(Long docsId);

    @Query(value = "SELECT d FROM Discussion d WHERE d.user.id = :userId GROUP BY d.id")
    List<Discussion> findAllByUser(Long userId);
}