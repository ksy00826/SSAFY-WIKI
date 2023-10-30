package com.bdos.ssafywiki.discussion.controller;

import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.discussion.service.DiscussionService;
import com.bdos.ssafywiki.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiscussionController {

    private final SimpMessageSendingOperations messageSendingTemplate;
    private final DiscussionService discusService;

    @MessageMapping("/{docsId}/chat")
    public void discuss(@PathVariable Long docsId, String discuss) {
        User user = User.builder().nickname("광표").build();
        DiscussionDto discussionDto = DiscussionDto
                .builder()
                .docsId(docsId)
                .content(discuss)
                .user(user)
                .build();
        messageSendingTemplate.convertAndSend("/sub/chat/room/" + docsId, discuss);
    }

    @GetMapping("/{docsId}/chatlist")
    public ResponseEntity<?> preChatList(@PathVariable Long docsId) {
        List<DiscussionDto> list = discusService.getPreChatList(docsId);
        return ResponseEntity.ok(list);
    }
}
