package com.bdos.ssafywiki.exception;

import lombok.Getter;

public enum ExceptionCode {

    INVALID_REFRESH_TOKEN(400,"Invalid Refresh Token"),
    INVALID_ACCESS_TOKEN(401,"Invalid Access Token"),
    EXPIRED_TIME_TOKEN(401,"Invalid Access Token"),
    MEMBER_UNAUTHORIZED(403,"Member Unauthorized"),
    EMAIL_NOT_FOUND(404, "Email Not Found"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    REVISION_NOT_FOUND(404, "Revision Not Found"),
    TEMPLATE_NOT_FOUND(404, "Template Not Found"),
    DOCUMENT_NOT_FOUND(404, "Document Not Found");


    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }

}


