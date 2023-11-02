package com.bdos.ssafywiki.discussion.controller;

import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.discussion.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DiscussionController {

    private final SimpMessageSendingOperations messageSendingTemplate;
    private final DiscussionService discussionService;


    @MessageMapping("/chat")
    public void discuss(DiscussionDto discussionDto) {
        String nickname = "sysy";
        Long userId = 1L;

        discussionDto.setNickname(nickname);
        discussionDto.setCreatedAt(LocalDateTime.now().toString());
        System.out.println(discussionDto);;
        messageSendingTemplate.convertAndSend("/sub/chat/" + discussionDto.getDocsId(), discussionDto);
        discussionService.saveMessage(discussionDto, userId);

    }

    @GetMapping("/api/chatlist/{docsId}")
    public ResponseEntity<?> preChatList(@PathVariable Long docsId) {
        List<DiscussionDto> list = discussionService.loadMessage(docsId);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
