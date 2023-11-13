package com.bdos.ssafywiki.user.service;

import com.bdos.ssafywiki.diff.MyDiffUtils;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.report.entity.DocumentReport;
import com.bdos.ssafywiki.report.enums.Status;
import com.bdos.ssafywiki.report.repository.DocumentReportRepository;
import com.bdos.ssafywiki.revision.entity.Content;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.repository.RevisionRepository;
import com.bdos.ssafywiki.user.dto.AdminDto;
import com.bdos.ssafywiki.user.entity.GuestUser;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Role;
import com.bdos.ssafywiki.user.repository.UserRepository;
import com.github.difflib.DiffUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final UserRepository userRepository;
    private final DocumentReportRepository documentReportRepository;
    private final DocumentRepository documentRepository;
    private final RevisionRepository revisionRepository;
    private final MyDiffUtils myDiffUtils;

    public Page<DocumentReport> getDocumentReports(User user, Pageable pageable) {
        if (user == null) user = new GuestUser();

        // user가 관리자인지 확인
        if (!Role.ROLE_ADMIN.equals(user.getRole())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }

        return documentReportRepository.findAllDocumentReport(pageable);
    }

    @Transactional
    public DocumentReport deleteDocs(AdminDto.DocumentReport requestBody) {
        long docsId = requestBody.getDocsId();
        long reportId = requestBody.getReportId();

        Document document = documentRepository.findById(docsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));
        document.setDeleted(true);
        documentRepository.save(document);

        DocumentReport documentReport = documentReportRepository.findById(reportId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
        documentReport.setStatus(Status.Complete);
        documentReportRepository.save(documentReport);

        Revision topRevision = revisionRepository.findTop1ByDocumentOrderByIdDesc(document);
        String content = topRevision.getContent() != null ? topRevision.getContent().getText() : null;

        if(content == null) {
            return documentReport;
        }

        long diffAmount = myDiffUtils.diffLength(DiffUtils.diff(myDiffUtils.splitIntoLines(content), myDiffUtils.splitIntoLines("")));
        Revision revision = Revision.builder()
                .user(documentReport.getUser())
                .document(document)
                .diffAmount(diffAmount)
                .parent(topRevision)
                .number(topRevision.getNumber() + 1)
                .build();

        revisionRepository.save(revision);
        return documentReport;
    }

    @Transactional
    public DocumentReport rejectReport(Long reportId) {
        DocumentReport documentReport = documentReportRepository.findById(reportId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
        documentReport.setStatus(Status.Reject);
        documentReportRepository.save(documentReport);
        return documentReport;
    }
}
