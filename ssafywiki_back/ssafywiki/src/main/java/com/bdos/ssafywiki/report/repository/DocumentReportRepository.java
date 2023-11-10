package com.bdos.ssafywiki.report.repository;

import com.bdos.ssafywiki.report.entity.DocumentReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentReportRepository extends JpaRepository<DocumentReport, Long> {
    @Query(value = "SELECT dr FROM DocumentReport dr LEFT JOIN FETCH dr.document LEFT JOIN FETCH dr.user")
    Page<DocumentReport> findAllDocumentReport(Pageable pageable);


}
