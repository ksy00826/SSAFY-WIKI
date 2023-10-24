package com.bdos.ssafywiki.revision.repository;

import com.bdos.ssafywiki.revision.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
