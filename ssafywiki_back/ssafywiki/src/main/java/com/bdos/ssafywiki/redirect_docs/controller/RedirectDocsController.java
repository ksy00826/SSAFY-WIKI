package com.bdos.ssafywiki.redirect_docs.controller;

import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.redirect_docs.dto.RedirectDocsDto;
import com.bdos.ssafywiki.redirect_docs.service.RedirectDocsService;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "리다이렉트 문서 API", description = "문서에 대한 CRUD 작업을 수행하는 API")
@RestController
@RequiredArgsConstructor
public class RedirectDocsController {

    private final RedirectDocsService redirectDocsService;

    @Operation(summary = "리다이렉트 문서 작성하기", description = "리다이렉트 문서 하나를 작성하고, 리다이렉트 검색어를 등록합니다.")
    @PostMapping("/api/redirect-docs")
    public ResponseEntity<RevisionDto.DocsResponse> writeRedirectDocs(@RequestBody RedirectDocsDto.Post post,
                                                              @AuthenticationPrincipal User userDetails){
        RevisionDto.DocsResponse response = redirectDocsService.writeDocs(post, userDetails);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "리다이렉트 키워드 가져오기", description = "문서에 해당하는 리다이렉트 키워드를 가져옵니다.")
    @GetMapping("/api/redirect-docs/{docsId}")
    public ResponseEntity<RedirectDocsDto.Detail> readRedirectDocs(@PathVariable Long docsId,
                                                                      @AuthenticationPrincipal User userDetails){
        RedirectDocsDto.Detail response = redirectDocsService.readDocs(docsId, userDetails);

        return ResponseEntity.ok(response);
    }
}
