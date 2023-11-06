package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.configuration.jwt.JwtTokenProvider;
import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.mapper.RevisionMapper;
import com.bdos.ssafywiki.revision.service.RevisionService;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.mapper.UserMapper;
import com.bdos.ssafywiki.user.mapper.UserMapperImpl;
import com.bdos.ssafywiki.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저 API", description = "마이페이지")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RevisionService revisionService;
    private final RevisionMapper revisionMapper;
    private final UserMapper userMapper;
    @GetMapping("/info")
    public ResponseEntity<UserDto.Registration> getUserInfo(@AuthenticationPrincipal User user) {
        UserDto.Registration response = userService.checkUserInfo(user.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/info")
    public ResponseEntity<String> editUserInfo(@RequestBody UserDto.Registration request,
                                               @AuthenticationPrincipal User user
             ) {

        String response = userService.editUser(user , request );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/info/contributeDocs")
    public ResponseEntity<List<RevisionDto.Version>> contributeDocs(
            @AuthenticationPrincipal User user){

        List<RevisionDto.Version> revisions = revisionService.getUserHistory(user.getId());
        return new ResponseEntity(revisionMapper, HttpStatus.OK);
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
}
