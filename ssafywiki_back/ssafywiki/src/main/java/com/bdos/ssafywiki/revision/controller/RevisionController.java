package com.bdos.ssafywiki.revision.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "버전 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/version")
public class RevisionController {

    @Operation(summary = "문서 버전 목록", description = "두개의 버전을 비교합니다.")
    @GetMapping("/{docs_id}")
    public ResponseEntity getHistory(
            @PathVariable("docs_id") long docsId,
            @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "버전 비교", description = "두개의 버전을 비교합니다.")
    @GetMapping("/compare")
    public ResponseEntity getDiff(@RequestParam long rev, @RequestParam(name = "oldrev") long oldRev){

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "버전 디테일", description = "해당 버전의 내용을 확인합니다.")
    @GetMapping("/{docs_id}/{rev_id}")
    public ResponseEntity getDetail(@PathVariable long docsId, @PathVariable long revId){

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "버전 되돌리기", description = "해당 버전으로 되돌립니다.")
    @PostMapping("/revoke")
    public ResponseEntity revokeVersion(@RequestParam(name = "select") int revId){

        return new ResponseEntity(HttpStatus.OK);
    }

}
