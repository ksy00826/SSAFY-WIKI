package com.bdos.ssafywiki.report.service;

import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.report.entity.DocumentReport;
import com.bdos.ssafywiki.report.repository.DocumentReportRepository;
import com.bdos.ssafywiki.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final DocumentReportRepository documentReportRepository;
    private final DocumentRepository documentRepository;

    @Transactional
    public void requestDocumentReport(Long docsId, User user) {
        Document document = documentRepository.findById(docsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));
        DocumentReport documentReport = DocumentReport.builder().document(document).user(user).build();

        documentReportRepository.save(documentReport);
    }
}
