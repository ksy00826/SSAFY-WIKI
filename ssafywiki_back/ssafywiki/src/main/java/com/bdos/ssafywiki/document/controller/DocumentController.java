package com.bdos.ssafywiki.document.controller;

import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.document.service.DocumentService;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "문서 API", description = "문서에 대한 CRUD 작업을 수행하는 API")
@RestController
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "문서 작성하기", description = "문서 하나를 작성합니다.")
    @PostMapping("/api/docs")
    public ResponseEntity<RevisionDto.Response> writeDocs(@RequestBody DocumentDto.Post post){
        RevisionDto.Response response = documentService.writeDocs(post);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "문서 상세 조회하기", description = "문서 하나의 상세를 조회합니다.")
    @GetMapping("/api/docs/{docsId}")
    public ResponseEntity<RevisionDto.Response> readDocs(@PathVariable Long docsId){
        RevisionDto.Response response = documentService.readDocs(docsId);

        return ResponseEntity.ok(response);
    }
}
