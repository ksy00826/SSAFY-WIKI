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

}
