package com.bdos.ssafywiki.revision.dto;

import com.bdos.ssafywiki.docs_category.dto.CategoryDto;
import com.bdos.ssafywiki.docs_category.dto.DocsCategoryDto;
import com.bdos.ssafywiki.docs_category.entity.Category;
import com.bdos.ssafywiki.docs_category.entity.DocsCategory;
import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RevisionDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class UpdateResponse {
        private Long docsId;
        private Long revId;
        private String title;
        private String content;
        private String comment;
        private Long topRevId;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
        private ExceptionCode exceptionCode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class DocsResponse {
        private Long docsId;
        private String author;
        private String title;
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
        private List<CategoryDto.Detail> categoryList;
        private boolean redirect;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class CheckUpdateResponse {
        private Long docsId;
        private String title;
        private String content;
        private List<String> categoryList;
        private boolean canUpdate;
        private Long revId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Version {
        private Long id;
        private Long number;
        private Long diffAmount;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
        private String comment;
        private Long originId;
        private Long originNumber;
        private UserDto.Version user;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Detail {
        private Long id;
        private Long number;
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
        private DocumentDto.Detail document;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class VersionPage {
        private List<Version> content;
        private long totalPages;
        private long totalElements;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class UserContribute {
        private Long docsId;
        private String title;
        private List<ContributeDetail> revisions = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ContributeDetail {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        private Long revisionId;
        private String revisionComment;
    }
}
