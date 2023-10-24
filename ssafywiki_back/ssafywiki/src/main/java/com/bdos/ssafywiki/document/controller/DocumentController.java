package com.bdos.ssafywiki.document.controller;

import com.bdos.ssafywiki.document.DocumentWriteDto;
import com.bdos.ssafywiki.document.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "문서 API", description = "문서에 대한 CRUD 작업을 수행하는 API")
@RestController
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "문서 작성하기", description = "문서 하나를 작성")
    @PostMapping("/api/docs")
    public ResponseEntity<?> writeDocs(@RequestBody DocumentWriteDto documentWriteDto){


    }
}
