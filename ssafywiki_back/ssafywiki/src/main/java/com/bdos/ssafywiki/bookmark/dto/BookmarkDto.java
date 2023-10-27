package com.bdos.ssafywiki.bookmark.dto;

import lombok.*;
import org.springframework.stereotype.Component;

public class BookmarkDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Detail{
        private Long docsId;
        private String title;
    }
}
