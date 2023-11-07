package com.bdos.ssafywiki.redis.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    private Map<String, ChannelTopic> topics;
    @PostConstruct
    private void init() {
        topics = new HashMap<>();
    }
    public void enterRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        log.info("토픽 생성 :" + roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }
    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
