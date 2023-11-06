package com.bdos.ssafywiki.document.controller;

import com.bdos.ssafywiki.discussion.service.DiscussionService;
import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.document.service.DocumentService;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Tag(name = "문서 API", description = "문서에 대한 CRUD 작업을 수행하는 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;
    private final DiscussionService discussionService;
    @Operation(summary = "문서 작성하기", description = "문서 하나를 작성합니다.")
    @PostMapping("/api/docs")
    public ResponseEntity<RevisionDto.DocsResponse> writeDocs(@RequestBody DocumentDto.Post post,
                                                              @AuthenticationPrincipal User userDetails){
        RevisionDto.DocsResponse response = documentService.writeDocs(post, userDetails);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "문서 상세 조회하기", description = "문서 하나의 상세를 조회합니다.")
    @GetMapping(value = {"/api/docs/{docsId}", "/api/docs/{docsId}/{revId}"})
    public ResponseEntity<RevisionDto.DocsResponse> readDocs(@PathVariable Long docsId,
                                                             @PathVariable(required = false) Long revId,
                                                             @AuthenticationPrincipal User userDetails){
        RevisionDto.DocsResponse response = documentService.readDocs(docsId, revId, userDetails);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "문서 수정 권한 체크", description = "문서 하나의 수정 권한이 있는지 여부를 확인합니다.")
    @GetMapping("/api/docs/update/{docsId}")
    public ResponseEntity<RevisionDto.CheckUpdateResponse> checkUpdateDocs(@PathVariable Long docsId,
                                                                           @AuthenticationPrincipal User userDetails){
        RevisionDto.CheckUpdateResponse response = documentService.checkUpdateDocs(docsId, userDetails);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "문서 수정하기", description = "문서 하나를 수정합니다.")
    @PutMapping("/api/docs")
    public ResponseEntity<RevisionDto.DocsResponse> updateDocs(@RequestBody DocumentDto.Put put,
                                                               @AuthenticationPrincipal User userDetails){
        RevisionDto.DocsResponse response = documentService.updateDocs(put, userDetails);

        return ResponseEntity.ok(response);
    }
}
