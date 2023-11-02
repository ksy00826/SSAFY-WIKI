package com.bdos.ssafywiki.discussion.dto;

import com.bdos.ssafywiki.user.entity.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class DiscussionDto {
    private Long docsId;
    private String nickname;
    private String content;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @Builder
    public DiscussionDto(Long docsId, String nickname, String content, LocalDateTime createdAt) {
        this.docsId = docsId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }

}
