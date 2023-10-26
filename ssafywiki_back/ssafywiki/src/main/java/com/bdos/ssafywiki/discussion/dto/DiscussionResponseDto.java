package com.bdos.ssafywiki.discussion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DiscussionResponseDto {
    private Long docsId;
    private String userNickname;
    private String message;
    private LocalDateTime createdAt;
}
