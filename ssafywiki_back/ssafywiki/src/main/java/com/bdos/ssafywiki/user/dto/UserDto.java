package com.bdos.ssafywiki.user.dto;

import lombok.*;

public class UserDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Version{
        private Long id;
        private String nickname;
    }
}
