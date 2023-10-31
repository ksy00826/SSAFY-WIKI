package com.bdos.ssafywiki.discussion.dto;

import com.bdos.ssafywiki.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DiscussionDto {
    private Long docsId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public DiscussionDto(Long docsId, String nickname, String content, LocalDateTime createdAt) {
        this.docsId = docsId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }
}
