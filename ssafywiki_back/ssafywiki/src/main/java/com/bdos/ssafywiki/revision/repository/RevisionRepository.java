package com.bdos.ssafywiki.revision.repository;

import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.dto.RevisionDto.Version;
import com.bdos.ssafywiki.revision.entity.Revision;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RevisionRepository extends JpaRepository<Revision, Long> {

    Revision findTop1ByDocumentOrderByIdDesc(Document document);

    @Query(value = "SELECT r FROM Revision r LEFT JOIN FETCH r.comment WHERE r.document.id = :docsId")
    Page<Revision> findAllByDocumentJoinComment(Long docsId, Pageable pageable);

    @Query(value = "SELECT r FROM Revision r LEFT JOIN FETCH r.content LEFT JOIN FETCH r.document WHERE r.document.id = :docsId AND r.number = :number")
    Revision findByDocumentIdAndNumber(Long docsId, Long number);

    @Query(value = "SELECT r FROM Revision r WHERE r.user.id = :userId")
    List<RevisionDto.Version> findAllByUser(Long userId);

}
