package com.bdos.ssafywiki.revision.repository;

import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.revision.entity.Revision;

import java.time.LocalDateTime;
import java.util.List;

import com.bdos.ssafywiki.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RevisionRepository extends JpaRepository<Revision, Long> {

    Revision findTop1ByDocumentOrderByIdDesc(Document document);

    @Query(value = "SELECT r FROM Revision r LEFT JOIN FETCH r.comment WHERE r.document.id = :docsId")
    Page<Revision> findAllByDocumentJoinComment(Long docsId, Pageable pageable);

    @Query(value = "SELECT r FROM Revision r LEFT JOIN FETCH r.content LEFT JOIN FETCH r.document WHERE r.document.id = :docsId AND r.id = :revId")
    Revision findByDocumentIdAndRevisionId(Long docsId, Long revId);

    @Query(value = "SELECT r FROM Revision r WHERE r.user.id = :userId")
    List<Revision> findAllByUser(Long userId);

    @Query(value = "SELECT r FROM Revision r WHERE r.user.id = :userId AND r.createdAt >= :startDate")
    List<Revision> findByUserWithStartDate(Long userId, LocalDateTime startDate);

    @Query(value = "SELECT r FROM Revision r JOIN FETCH r.document WHERE r.user.id = :userId AND r.createdAt >= :startDate AND r.createdAt < :endDate")
    List<Revision> findByUserWithDate(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    @Query(value = "SELECT r FROM Revision r JOIN FETCH r.document WHERE r.user.id = :userId AND r.createdAt >= :startDate AND r.createdAt < :endDate GROUP BY r.document")
    List<Revision> findByDocsUserWithDate(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT r FROM Revision r WHERE r.user = :user AND r.document = :document")
    List<Revision> findRevisionInfoByUserAndDateAndDocs(Document document, User user);
}
