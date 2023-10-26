package com.bdos.ssafywiki.discussion;

import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.discussion.service.DiscusService;
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
    private final DiscusService discusService;

    @MessageMapping("/{docsId}/chat")
    public void discuss(@PathVariable Long docsId, String discuss) {
        String nickname = "광표";
        DiscussionDto discussionDto = DiscussionDto
                .builder()
                .docsId(docsId)
                .message(discuss)
                .userNickname(nickname)
                .build();
        messageSendingTemplate.convertAndSend("/sub/chat/room/" + docsId, discuss);
    }

    @GetMapping("/{docsId}/chatlist")
    public ResponseEntity<?> preChatList(@PathVariable Long docsId) {
        List<DiscussionDto> list = discusService.getPreChatList(docsId);
        return ResponseEntity.ok(list);
    }
}
