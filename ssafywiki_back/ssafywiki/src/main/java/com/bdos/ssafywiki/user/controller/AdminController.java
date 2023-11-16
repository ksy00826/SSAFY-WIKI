package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.report.entity.DocumentReport;
import com.bdos.ssafywiki.report.mapper.ReportMapper;
import com.bdos.ssafywiki.user.dto.AdminDto;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Role;
import com.bdos.ssafywiki.user.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "신고 목록 불러오기")
    @GetMapping("/docs-report")
    public ResponseEntity getDocsReport(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<DocumentReport> documentReportPage = adminService.getDocumentReports(user, pageable);


        return new ResponseEntity(reportMapper.toDtoPage(documentReportPage), HttpStatus.OK);
    }
    @Operation(summary = "신고된 문서 삭제")
    @PostMapping("/docs-report")
    public ResponseEntity<Long> deleteDocs(
            @AuthenticationPrincipal User user,
            @RequestBody AdminDto.DocumentReport requestBody) {

        DocumentReport documentReport = adminService.deleteDocs(requestBody);

        return new ResponseEntity<>(documentReport.getId(), HttpStatus.OK);
    }

    @Operation(summary = "신고 반려")
    @DeleteMapping("/docs-report/{reportId}")
    public ResponseEntity<Long> rejectReport(
            @AuthenticationPrincipal User user,
            @PathVariable("reportId") Long reportId) {
        DocumentReport documentReport = adminService.rejectReport(reportId);

        return new ResponseEntity<>(documentReport.getId(), HttpStatus.OK);
    }
}
