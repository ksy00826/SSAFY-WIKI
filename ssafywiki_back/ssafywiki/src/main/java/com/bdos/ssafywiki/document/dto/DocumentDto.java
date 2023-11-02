package com.bdos.ssafywiki.document.dto;

import lombok.*;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        private List<String> categories;
        private Long readAuth;
        private Long writeAuth;
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
