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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Registration {
        private String name;
        private String email;
        private String password;
        private String roll;
        private String campus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Login {
        private String email;
        private String password;
    }
}
