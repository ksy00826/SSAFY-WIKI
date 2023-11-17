package com.bdos.ssafywiki.docs_category.dto;

import lombok.*;

public class DocsCategoryDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class DocsCategoryDetail {
        private Long docsCategoryId;
        private Long categoryId;
        private String categoryName;
    }
}
