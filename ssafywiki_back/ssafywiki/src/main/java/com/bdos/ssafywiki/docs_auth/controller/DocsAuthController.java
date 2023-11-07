package com.bdos.ssafywiki.docs_auth.controller;

import com.bdos.ssafywiki.docs_auth.dto.DocsAuthDto;
import com.bdos.ssafywiki.docs_auth.service.DocsAuthService;
import com.bdos.ssafywiki.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "문서 권한 API", description = "문서에 대한 권한을 확인하고 설정할 수 있음")
@RestController
@RequiredArgsConstructor
public class DocsAuthController {

    private final DocsAuthService docsAuthService;

    @Operation(summary = "문서 권한 조회하기", description = "문서 하나의 권한을 조회합니다.")
    @GetMapping("api/docs/auth/{docsId}")
    public ResponseEntity<DocsAuthDto.AuthResponse> getAuth (@PathVariable Long docsId,
                                                             @AuthenticationPrincipal User userDetails) {
        DocsAuthDto.AuthResponse response = docsAuthService.getAuth(docsId, userDetails);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "문서 권한 변경하기", description = "문서 하나의 권한을 변경합니다.")
    @PostMapping("api/docs/auth")
    public ResponseEntity<Boolean> getAuth (@RequestBody DocsAuthDto.AuthRequest request,
                                                             @AuthenticationPrincipal User userDetails) {
        Boolean response = docsAuthService.updateAuth(request, userDetails);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "문서 권한 유저 초대", description = "문서 하나의 사용자권한에 멤버를 초대합니다.")
    @PostMapping("api/docs/auth/member")
    public ResponseEntity<Boolean> invitMember (@RequestBody DocsAuthDto.MemberInviteRequest request,
                                                             @AuthenticationPrincipal User userDetails) {
        Boolean response = docsAuthService.inviteMember(request, userDetails);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "문서 권한 유저 삭제", description = "문서 하나의 사용자권한에 멤버를 삭제합니다.")
    @DeleteMapping("api/docs/auth/member")
    public ResponseEntity<Boolean> deleteMember (@RequestBody DocsAuthDto.MemberDeleteRequest request,
                                                                 @AuthenticationPrincipal User userDetails) {
        Boolean response = docsAuthService.deleteMember(request, userDetails);

        return ResponseEntity.ok(response);
    }
}
