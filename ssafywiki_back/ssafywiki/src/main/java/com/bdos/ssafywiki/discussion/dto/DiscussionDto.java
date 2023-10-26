package com.bdos.ssafywiki.discussion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DiscussionDto {
    private Long docsId;
    private String userNickname;
    private String message;
    private LocalDateTime createdAt;

    @Builder
    public DiscussionDto(Long docsId, String userNickname, String message) {
        this.docsId = docsId;
        this.userNickname = userNickname;
        this.message = message;
    }
}
