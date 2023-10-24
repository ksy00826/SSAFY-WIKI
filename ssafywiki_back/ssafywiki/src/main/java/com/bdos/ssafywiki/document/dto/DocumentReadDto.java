package com.bdos.ssafywiki.document.dto;

import lombok.Getter;

@Getter
public class DocumentReadDto {
    private Long docsId;
    private Long userId;
    private String title;
    private String content;

    public DocumentReadDto(Long docsId, Long userId, String title, String content) {
        this.docsId = docsId;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
