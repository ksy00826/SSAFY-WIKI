package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.report.entity.DocumentReport;
import com.bdos.ssafywiki.report.mapper.ReportMapper;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Role;
import com.bdos.ssafywiki.user.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// admin 유저 확인용
@Tag(name = "ADMIN API", description = "ADMIN 권한")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final ReportMapper reportMapper;

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/superadmin")
    public String superadmin() {
        return "superadmin";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @GetMapping("/docs-report")
    public ResponseEntity getDocsReport(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<DocumentReport> documentReportPage = adminService.getDocumentReports(user, pageable);


        return new ResponseEntity(reportMapper.toDtoPage(documentReportPage), HttpStatus.OK);
    }
}
