package com.bdos.ssafywiki.revision.repository;

import com.bdos.ssafywiki.revision.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
}
