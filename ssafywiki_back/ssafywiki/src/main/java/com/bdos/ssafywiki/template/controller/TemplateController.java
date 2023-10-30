package com.bdos.ssafywiki.template.controller;

import com.bdos.ssafywiki.template.dto.TemplateDto;
import com.bdos.ssafywiki.template.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "템플릿 목록 불러오기", description = "탬플릿 목록을 불러옵니다.")
    @GetMapping("/api/docs/template")
    public ResponseEntity<List<TemplateDto.Preview>> readTemplateList(@PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<TemplateDto.Preview> list = templateService.readTemplateList(pageable);

        if (list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "템플릿 상세 불러오기", description = "탬플릿 상세를 불러옵니다.")
    @GetMapping("/api/docs/template/{templateId}")
    public ResponseEntity<TemplateDto.Detail> readTemplateDetail(@PathVariable Long templateId){
        TemplateDto.Detail templateDetail = templateService.readTemplateDetail(templateId);

        return ResponseEntity.ok(templateDetail);
    }

    @Operation(summary = "템플릿 하나 삭제하기", description = "자신이 작성한 템플릿을 삭제합니다")
    @DeleteMapping("/api/docs/template/{templateId}")
    public ResponseEntity<List<TemplateDto.Preview>> deleteTemplate(@PathVariable Long templateId,
                                                                    @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        templateService.deleteTemplate(templateId);
        //삭제 후 목록 반환하기
        List<TemplateDto.Preview> list = templateService.readTemplateList(pageable);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "템플릿 검색하기", description = "이름에 키워드가 포함된 템플릿을 검색합니다")
    @GetMapping("/api/docs/template/search")
    public ResponseEntity<List<TemplateDto.Preview>> searchTemplate(@RequestParam("keyword") String keyword,
                                                                    @RequestParam("isMyTemplate") boolean isMyTemplate,
                                                                    @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<TemplateDto.Preview> list = templateService.searchTemplate(keyword, isMyTemplate, pageable);

        return ResponseEntity.ok(list);
    }
}
