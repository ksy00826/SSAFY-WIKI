package com.bdos.ssafywiki.exception;

import lombok.Getter;

public enum ExceptionCode {

    INVALID_REFRESH_TOKEN(400,"Invalid Refresh Token"),
    INVALID_ACCESS_TOKEN(401,"Invalid Access Token"),
    EXPIRED_TIME_TOKEN(401,"Invalid Access Token"),
    MEMBER_UNAUTHORIZED(403,"Member Unauthorized"),
    EMAIL_NOT_FOUND(404, "Email Not Found"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_EXIST(404, "Member Already Exist"),
    BLOCKED_USER(404, "Member is blocked"),
    REVISION_NOT_FOUND(404, "Revision Not Found"),
    TEMPLATE_NOT_FOUND(404, "Template Not Found"),
    MERGE_FAILED(404, "Merge Failed"),
    DOCUMENT_NOT_FOUND(404, "Document Not Found"),
    BOOKMARK_NOT_FOUND(404, "Bookmark Not Found"),
    REQUEST_NOT_FOUND(404,"Requset Not Found"),
    BOOKMARK_CONFLICT(409, "Conflict"),
    DOCUMENT_NO_ACCESS(402, "Document Can Not Access");


    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }

}


