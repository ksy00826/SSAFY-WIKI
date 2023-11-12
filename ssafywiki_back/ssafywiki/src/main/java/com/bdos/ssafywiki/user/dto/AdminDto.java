package com.bdos.ssafywiki.user.dto;

import lombok.*;

public class AdminDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class DocumentReport{
        private Long docsId;
        private Long reportId;
    }
}
