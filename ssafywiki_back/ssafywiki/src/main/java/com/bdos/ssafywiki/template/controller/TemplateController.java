package com.bdos.ssafywiki.template.controller;

import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.template.dto.TemplateDto;
import com.bdos.ssafywiki.template.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "템플릿 API", description = "템플릿에 대한 CRUD 작업을 수행하는 API")
@RestController
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @Operation(summary = "템플릿 생성하기", description = "탬플릿 하나를 생성합니다.")
    @PostMapping("/api/docs/template")
    public ResponseEntity<TemplateDto.Detail> createTemplate(@RequestBody TemplateDto.Post post){
        TemplateDto.Detail detail = templateService.createTemplate(post);

        return ResponseEntity.ok(detail);
    }
}
