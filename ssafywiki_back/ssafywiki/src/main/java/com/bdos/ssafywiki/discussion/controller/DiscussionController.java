package com.bdos.ssafywiki.discussion.controller;

import com.bdos.ssafywiki.configuration.jwt.JwtTokenProvider;
import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.discussion.service.DiscussionService;
import com.bdos.ssafywiki.redis.service.RedisPublisher;
import com.bdos.ssafywiki.redis.service.TopicService;
import com.bdos.ssafywiki.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.Topic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "토론 API", description = "토론에 대한 작업을 수행하는 API")
public class DiscussionController {

    private final RedisPublisher redisPublisher;
    private final DiscussionService discussionService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TopicService topicService;

    @MessageMapping("/chat")
    public void discuss(DiscussionDto discussionDto, @Header("Authorization") String token) {

        System.out.println(token);
        User user = jwtTokenProvider.getUserByToken(token) ;
        log.info(user.toString());
        discussionDto.setNickname(user.getNickname());
        discussionDto.setEmail(user.getEmail());
        discussionDto.setCreatedAt(LocalDateTime.now().toString());
        System.out.println(discussionDto);
        redisPublisher.publish(topicService.getTopic(discussionDto.getDocsId().toString()), discussionDto);
        discussionService.saveMessage(discussionDto, user);

    }

    @GetMapping("/api/chatlist/{docsId}")
    public ResponseEntity<?> preChatList(@PathVariable Long docsId) {
        List<DiscussionDto> list = discussionService.loadMessage(docsId);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
