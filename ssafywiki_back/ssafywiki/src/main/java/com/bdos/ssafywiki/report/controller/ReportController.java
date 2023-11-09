package com.bdos.ssafywiki.report.controller;

import com.bdos.ssafywiki.report.service.ReportService;
import com.bdos.ssafywiki.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "신고 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;
    @PostMapping("/docs")
    public ResponseEntity requestDocumentReport(
            @AuthenticationPrincipal User user,
            @PathVariable("docs_id") Long docsId){
        reportService.requestDocumentReport(docsId, user);

        return new ResponseEntity(HttpStatus.OK);
    }
}
