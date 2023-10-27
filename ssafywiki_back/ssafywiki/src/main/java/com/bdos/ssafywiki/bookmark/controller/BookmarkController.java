package com.bdos.ssafywiki.bookmark.controller;

import com.bdos.ssafywiki.bookmark.dto.BookmarkDto;
import com.bdos.ssafywiki.bookmark.service.BookmarkService;
import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "문서 북마크 API", description = "문서에 대한 북마크 CRD 작업을 수행하는 API")
@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 등록하기", description = "문서 하나를 북마크합니다.")
    @PostMapping("/api/docs/bookmark/{docsId}")
    public ResponseEntity<?> writeBookmark(@PathVariable Long docsId){
        bookmarkService.registBookmark(docsId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "북마크 목록 조회하기", description = "사용자가 북마크한 문서 목록을 불러옵니다.")
    @GetMapping ("/api/docs/bookmark")
    public ResponseEntity<List<BookmarkDto.Detail>> readBookmark(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<BookmarkDto.Detail> list = bookmarkService.getBookmark(pageable);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "북마크 취소하기", description = "사용자가 북마크한 문서의 북마크를 취소합니다.")
    @DeleteMapping ("/api/docs/bookmark/{docsId}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Long docsId){
        bookmarkService.deleteBookmark(docsId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
