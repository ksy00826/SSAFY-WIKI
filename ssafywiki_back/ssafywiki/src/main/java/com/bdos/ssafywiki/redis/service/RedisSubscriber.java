package com.bdos.ssafywiki.redis.service;

import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String channelName = new String(message.getChannel());

            // key 값에 따라 다른 DTO 클래스 선택
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            Class<?> dtoClass = null;
            // redis에서 발행된 데이터를 받아 deserialize
            // Websocket 구독자에게 채팅 메시지 Send
            if ("recent".equals(channelName)) {
                DocumentDto.Recent roomMessage = objectMapper.readValue(publishMessage, DocumentDto.Recent.class);
                messagingTemplate.convertAndSend("/sub/recent/", roomMessage);
            } else {
                DiscussionDto roomMessage = objectMapper.readValue(publishMessage, DiscussionDto.class);
                messagingTemplate.convertAndSend("/sub/chat/" + roomMessage.getDocsId(), roomMessage);
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}