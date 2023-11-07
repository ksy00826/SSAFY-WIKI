package com.bdos.ssafywiki.redirect_docs.dto;

import lombok.*;

public class RedirectDocsDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Post {
        private String title;
        private String redirectTitle;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Detail {
        private String originDocsTitle;
        private String originDocsId;
    }
}
