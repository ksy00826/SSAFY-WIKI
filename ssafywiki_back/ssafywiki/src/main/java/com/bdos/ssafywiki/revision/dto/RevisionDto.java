package com.bdos.ssafywiki.revision.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class RevisionDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Response {
        private Long docsId;
        private String author;
        private String title;
        private String content;
        private boolean deleted;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
