package com.bdos.ssafywiki.discussion.repository;


import com.bdos.ssafywiki.discussion.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findTop100ByDocumentIdOrderByCreatedAtAsc(Long docsId);

    Discussion findTopByDocumentIdOrderByCreatedAtDesc(Long docsId);
}