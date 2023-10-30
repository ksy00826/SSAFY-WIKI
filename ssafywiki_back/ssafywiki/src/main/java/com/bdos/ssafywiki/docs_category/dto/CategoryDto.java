package com.bdos.ssafywiki.docs_category.dto;

import lombok.*;

public class CategoryDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Detail{
        private Long id;
        private String name;
    }
}
