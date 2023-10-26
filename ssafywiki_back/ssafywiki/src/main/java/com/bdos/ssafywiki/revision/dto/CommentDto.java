package com.bdos.ssafywiki.revision.dto;

import lombok.*;

public class CommentDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Version{
        private Long id;
        private String content;
    }
}
