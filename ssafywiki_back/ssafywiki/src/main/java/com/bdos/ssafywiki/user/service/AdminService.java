package com.bdos.ssafywiki.user.service;

import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.report.entity.DocumentReport;
import com.bdos.ssafywiki.report.repository.DocumentReportRepository;
import com.bdos.ssafywiki.user.entity.GuestUser;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Role;
import com.bdos.ssafywiki.user.repository.UserRepository;
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

    public Page<DocumentReport> getDocumentReports(User user, Pageable pageable) {
        if (user == null) user = new GuestUser();

        // user가 관리자인지 확인
        if (!Role.ROLE_ADMIN.equals(user.getRole())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }

        return documentReportRepository.findAllDocumentReport(pageable);
    }
}
