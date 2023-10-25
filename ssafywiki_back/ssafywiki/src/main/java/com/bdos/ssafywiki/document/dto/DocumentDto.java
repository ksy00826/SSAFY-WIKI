package com.bdos.ssafywiki.document.dto;

import lombok.*;

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
    public static class Response {
        private Long docsId;
        private Long userId;
        private String title;
        private String content;
    }
}
