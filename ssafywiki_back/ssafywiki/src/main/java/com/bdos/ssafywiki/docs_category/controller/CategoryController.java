package com.bdos.ssafywiki.docs_category.controller;


import com.bdos.ssafywiki.docs_category.dto.CategoryDto;
import com.bdos.ssafywiki.docs_category.service.CategoryService;
import com.bdos.ssafywiki.template.dto.TemplateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "카테고리 API", description = "카테고리 CRD 작업을 수행하는 API")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록 불러오기", description = "카테고리 목록을 불러옵니다.")
    @GetMapping("/api/docs/category")
    public ResponseEntity<List<CategoryDto.Detail>> readCategoryList(){
        List<CategoryDto.Detail> list = categoryService.readTemplateList();

        if (list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(list);
    }

}
