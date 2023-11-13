package com.bdos.ssafywiki.document.controller;

import com.bdos.ssafywiki.docs_auth.dto.DocsAuthDto;
import com.bdos.ssafywiki.docs_auth.service.DocsAuthService;
import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.document.mapper.DocumentMapper;
import com.bdos.ssafywiki.document.service.DocumentService;
import com.bdos.ssafywiki.redis.service.RedisPublisher;
import com.bdos.ssafywiki.redis.service.TopicService;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "문서 API", description = "문서에 대한 CRUD 작업을 수행하는 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;
    private final DocsAuthService docsAuthService;
    private final TopicService topicService;
    private final RedisPublisher redisPublisher;
    private final DocumentMapper documentMapper;

    @Operation(summary = "문서 작성하기", description = "문서 하나를 작성합니다.")
    @PostMapping("/api/docs")
    public ResponseEntity<RevisionDto.DocsResponse> writeDocs(@RequestBody DocumentDto.Post post,
                                                              @AuthenticationPrincipal User userDetails){
        RevisionDto.DocsResponse response = documentService.writeDocs(post, userDetails);

        if(post.getReadAuth() == 100 || post.getWriteAuth() == 100) {
            docsAuthService.updateAuth(new DocsAuthDto.AuthRequest(response.getDocsId(), post.getReadAuth(), post.getWriteAuth()), userDetails);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "문서 상세 조회하기", description = "문서 하나의 상세를 조회합니다.")
    @GetMapping("/api/docs/{docsId}")
    public ResponseEntity<RevisionDto.DocsResponse> readDocs(@PathVariable Long docsId,
                                                             @RequestParam(required = false) Long revId,
                                                             @AuthenticationPrincipal User userDetails){
        topicService.enterRoom(docsId.toString());
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
    public ResponseEntity<RevisionDto.UpdateResponse> updateDocs(@RequestBody DocumentDto.Put put,
                                                               @AuthenticationPrincipal User userDetails){
        RevisionDto.UpdateResponse response = documentService.updateDocs(put, userDetails);
        if(response.getExceptionCode() != null){
            return new ResponseEntity(response, HttpStatus.CONFLICT);
        }else{
            redisPublisher.publish(topicService.getTopic("recent"), documentMapper.toRecent(response));
            return ResponseEntity.ok(response);
        }
    }


    @Operation(summary = "최근 수정한 문서 웹소켓 연결", description = "웹소켓을 연결합니다")
    @GetMapping("/api/docs/sub")
    public void recentUpdatedDocsSub() {
        topicService.enterRoom("recent");
    }

    @Operation(summary = "최근 수정한 문서 리스트 조회하기", description = "최근 수정한 문서를 10개 조회합니다.")
    @GetMapping("/api/docs/recent")
    public ResponseEntity<?> readRecentDocs() {
        List<DocumentDto.Recent> recentDocs = documentService.loadRecentDocsList();
        return ResponseEntity.ok(recentDocs);
    }


    @Operation(summary = "문서 리스트 조회하기", description = "문서 여럿의 상세를 조회합니다.")
    @PostMapping("/api/docs/list")
    public ResponseEntity<List<RevisionDto.DocsResponse>> readDocs(
            @RequestBody List<String> docsIds,
            @AuthenticationPrincipal User userDetails){
        List<RevisionDto.DocsResponse> response = new ArrayList<>();
        for(String id : docsIds){
            response.add(documentService.readDocs(Long.valueOf(id), null, userDetails));
        }

        return ResponseEntity.ok(response);
    }
}
