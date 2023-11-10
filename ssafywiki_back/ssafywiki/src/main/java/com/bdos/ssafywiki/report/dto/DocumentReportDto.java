package com.bdos.ssafywiki.report.dto;

import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class DocumentReportDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Page {
        private List<Response> content;
        private long totalPages;
        private long totalElements;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Response {
        private Long id;
        private DocumentDto.Report document;
        private UserDto.Version user;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
    }
}
