package com.bdos.ssafywiki.document.dto;

import lombok.*;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DocumentDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Post {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Put {
        private Long docsId;
        private String content;
        private String comment;
    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Detail{
        private Long id;
        private String title;
        // parent, children 추가 해야 할듯
    }
}
