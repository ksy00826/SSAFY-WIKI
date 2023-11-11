package com.bdos.ssafywiki.bookmark.controller;

import com.bdos.ssafywiki.bookmark.dto.BookmarkDto;
import com.bdos.ssafywiki.bookmark.service.BookmarkService;
import com.bdos.ssafywiki.configuration.jwt.CustomUserDetails;
import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "문서 북마크 API", description = "문서에 대한 북마크 CRD 작업을 수행하는 API")
@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 개수", description = "문서 하나의 북마크 개수 확인")
    @GetMapping("/api/docs/bookmark/cnt/{docsId}")
    public ResponseEntity<Integer> countBookmark(@PathVariable Long docsId){
        int result = bookmarkService.countBookmark(docsId);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "북마크 등록 여부 확인", description = "문서 하나의 북마크 여부 확인")
    @GetMapping("/api/docs/bookmark/{docsId}")
    public ResponseEntity<Boolean> checkBookmark(@PathVariable Long docsId,
                                           @AuthenticationPrincipal User userDetails){

        boolean result = bookmarkService.checkBookmark(docsId, userDetails);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "북마크 등록하기", description = "문서 하나를 북마크합니다.")
    @PostMapping("/api/docs/bookmark/{docsId}")
    public ResponseEntity<Integer> writeBookmark(@PathVariable Long docsId,
                                           @AuthenticationPrincipal User userDetails){
        int result = bookmarkService.registBookmark(docsId, userDetails);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "북마크 목록 조회하기", description = "사용자가 북마크한 문서 목록을 불러옵니다.")
    @GetMapping ("/api/docs/bookmark")
    public ResponseEntity<List<BookmarkDto.Detail>> readBookmark(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                                @AuthenticationPrincipal User userDetails){
        List<BookmarkDto.Detail> list = bookmarkService.getBookmark(pageable, userDetails);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "북마크 취소하기", description = "사용자가 북마크한 문서의 북마크를 취소합니다.")
    @DeleteMapping ("/api/docs/bookmark/{docsId}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Long docsId,
                                            @AuthenticationPrincipal User userDetails){
        int result = bookmarkService.deleteBookmark(docsId, userDetails);

        return ResponseEntity.ok(result);
    }
}
