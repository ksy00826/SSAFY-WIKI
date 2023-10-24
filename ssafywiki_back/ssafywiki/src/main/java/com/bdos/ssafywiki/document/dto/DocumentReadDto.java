package com.bdos.ssafywiki.document.dto;

import lombok.Getter;

@Getter
public class DocumentReadDto {
    private Long docsId;
    private Long userId;
    private String title;
    private String content;
}
