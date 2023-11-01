package com.bdos.ssafywiki.discussion.controller;

import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.discussion.service.DiscussionService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
public class DiscussionController {

    private final SimpMessageSendingOperations messageSendingTemplate;
    private final DiscussionService discussionService;


    @MessageMapping("/chat/{docsId}")
    public void discuss(@PathVariable Long docsId, String discuss) {
        String nickname = "광표";
        Long userId = 1L;
        DiscussionDto discussionDto = DiscussionDto
                .builder()
                .docsId(docsId)
                .content(discuss)
                .nickname(nickname)
                .createdAt(LocalDateTime.now())
                .build();
//        discussionService.enterMessageRoom(docsId.toString());
        messageSendingTemplate.convertAndSend("/sub/chat/" + docsId, discussionDto);
//        discussionService.saveMessage(discussionDto, userId);

    }

    @GetMapping("/chatlist/{docsId}")
    public ResponseEntity<?> preChatList(@PathVariable Long docsId) {
        List<DiscussionDto> list = discussionService.loadMessage(docsId);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
