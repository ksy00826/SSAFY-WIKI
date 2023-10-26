package com.bdos.ssafywiki.revision.dto;

import com.bdos.ssafywiki.user.dto.UserDto;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class RevisionDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Version{
        private Long id;
        private Long number;
        private Long diffAmount;
        private LocalDateTime createdAt;
        private CommentDto.Version comment;
        private UserDto.Version user;


    }
}
