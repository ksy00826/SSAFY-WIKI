package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.configuration.jwt.JwtTokenProvider;
import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.docs_auth.dto.DocsAuthDto;
import com.bdos.ssafywiki.docs_auth.service.DocsAuthService;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.report.dto.DocumentReportDto;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.mapper.RevisionMapper;
import com.bdos.ssafywiki.revision.service.RevisionService;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.GuestUser;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Role;
import com.bdos.ssafywiki.user.mapper.UserMapper;
import com.bdos.ssafywiki.user.mapper.UserMapperImpl;
import com.bdos.ssafywiki.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Tag(name = "유저 API", description = "마이페이지")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RevisionService revisionService;
    private final DocsAuthService docsAuthService;
    private final RevisionMapper revisionMapper;
    private final UserMapper userMapper;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal User user) {
        if (user.equals(null)) {
            return ResponseEntity.ok("false");
        }
        UserDto.Registration response = userService.checkUserInfo(user.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/info")
    public ResponseEntity<HttpStatus> editUserInfo(@RequestBody UserDto.Registration request,
                                               @AuthenticationPrincipal User user
    ) {

        HttpStatus status = userService.editUser(user, request);
        return new ResponseEntity<>(status);
    }

    @GetMapping("/info/contributeDocs")
    public ResponseEntity<List<RevisionDto.DocsResponse>> contributeDocs(
            @AuthenticationPrincipal User user) {

        List<RevisionDto.DocsResponse> revisions = revisionService.getUserHistory(user.getId());
        return new ResponseEntity(revisions, HttpStatus.OK);
    }
//    @GetMapping("/info/contributeDocs")
//    public ResponseEntity<?> getContributeDocs(@AuthenticationPrincipal User user) {
//        List<Document> list = userService.getDocs(user);
//        return new ResponseEntity(list, HttpStatus.OK);
//    }

    @GetMapping("/info/contributeChats")
    public ResponseEntity<?> getContributeChats(@AuthenticationPrincipal User user) {
        List<DiscussionDto> list = userService.getChats(user);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserEmailAndId(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userMapper.toVersion(user), HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<Boolean> isAdmin(@AuthenticationPrincipal User user) {
//        System.out.println("유저 확인요~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        if (user == null) user = new GuestUser();
        return new ResponseEntity<>(Role.ROLE_ADMIN.equals(user.getRole()), HttpStatus.OK);
    }

    @GetMapping("/info/contribute-docs")
    public ResponseEntity<int[][]> contributeDocsWithDate(
            @AuthenticationPrincipal User user, @RequestParam LocalDateTime startDate) {

        int[][] revisions = revisionService.getUserContributeDocs(user, startDate);
        return new ResponseEntity(revisions, HttpStatus.OK);
    }

    @GetMapping("/info/day-contribute-docs")
    public ResponseEntity<List<RevisionDto.UserContribute>> contributeDocsWithOneDate(
            @AuthenticationPrincipal User user, @RequestParam LocalDateTime date) {

        List<RevisionDto.UserContribute> revisions = revisionService.getUserContributeDocsWithDate(user, date);
        return new ResponseEntity(revisions, HttpStatus.OK);
    }

    @GetMapping("/info/contribute-docs/{userId}")
    public ResponseEntity<int[][]> contributeDocsWithDate(
            @RequestParam LocalDateTime startDate, @PathVariable("userId") Long userId) {

        int[][] revisions = revisionService.getUserContributeDocs(userId, startDate);
        return new ResponseEntity(revisions, HttpStatus.OK);
    }

    @GetMapping("/info/day-contribute-docs/{userId}")
    public ResponseEntity<List<RevisionDto.UserContribute>> contributeDocsWithOneDate(
            @RequestParam LocalDateTime date, @PathVariable("userId") Long userId) {

        List<RevisionDto.UserContribute> revisions = revisionService.getUserContributeDocsWithDate(userId, date);
        return new ResponseEntity(revisions, HttpStatus.OK);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable("id") Long userId) {
        User user = userService.getUserInfo(userId);

        if (user.equals(null)) {
            return ResponseEntity.ok("false");
        }
        return ResponseEntity.ok(userMapper.toInfo(user));
    }

    @Operation(summary = "내 그룹 목록", description = "유저가 프라이빗 권한을 가지고있는 문서 목록을 반환합니다.")
    @GetMapping("/myauth")
    public ResponseEntity<List<DocsAuthDto.SimpleDocs>> myDocs (@AuthenticationPrincipal User userDetails) {
        List<DocsAuthDto.SimpleDocs> response = docsAuthService.myDocs(userDetails);

        return ResponseEntity.ok(response);
    }
}
