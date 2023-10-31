package com.bdos.ssafywiki.user.dto;

import com.bdos.ssafywiki.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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
        private Role roll;
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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Token {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("refresh_token")
        private String refreshToken;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthenticationResponse {
        private  Long id;
        private String email;
        private List<String> roles;

        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("token_type")
        private String tokenType;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class RegisterRequest {

        @NotBlank(message = "firstname is required")
        private String firstname;
        @NotBlank(message = "lastname is required")
        private String lastname;
        @NotBlank(message = "email is required")
        @Email(message = "email format is not valid")
        private String email;
        @NotBlank(message = "password is required")
//        @StrongPassword
        private String password;
        @NotNull
        private Role role;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class AuthenticationRequest {
        private String email;
        private String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class RefreshTokenResponse {

        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("token_type")
        private String tokenType;

    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public class RefreshTokenRequest {
        private String refreshToken;

    }

}
