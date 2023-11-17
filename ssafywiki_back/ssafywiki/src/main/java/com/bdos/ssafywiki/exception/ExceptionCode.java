package com.bdos.ssafywiki.exception;

import lombok.Getter;

public enum ExceptionCode {

    INVALID_REFRESH_TOKEN(400,"Invalid Refresh Token"),
    INVALID_ACCESS_TOKEN(401,"Invalid Access Token"),
    EXPIRED_TIME_TOKEN(401,"Invalid Access Token"),
    MEMBER_UNAUTHORIZED(403,"Member Unauthorized"),
    EMAIL_NOT_FOUND(404, "존재하지 않는 사용자 입니다."),
    WRONG_PASSWORD(404, "비밀번호가 틀렸습니다."),
    MEMBER_NOT_FOUND(404, "존재하지 않는 사용자 입니다."),
    MEMBER_EXIST(404, "이미 존재하는 유저입니다."),
    BLOCKED_USER(404, "정지된 유저입니다."),
    REQUIRED_LOGIN(401, "로그인 권한이 필요합니다."),
    REQUIRED_MANAGER(401, "메니저 권한이 필요합니다."),
    REQUIRED_PRIVATE(401, "private문서 권한이 필요합니다."),
    REQUIRED_ADMIN(401, "관리자 권한이 필요합니다."),
    REVISION_NOT_FOUND(404, "Revision Not Found"),
    TEMPLATE_NOT_FOUND(404, "Template Not Found"),
    MERGE_FAILED(404, "Merge Failed"),
    DOCUMENT_NOT_FOUND(404, "Document Not Found"),
    REDIRECT_DOCUMENT_NOT_FOUND(404, "Redirect Document Not Found"),
    BOOKMARK_NOT_FOUND(404, "Bookmark Not Found"),
    REQUEST_NOT_FOUND(404,"Requset Not Found"),
    BOOKMARK_CONFLICT(409, "Conflict"),
    DOCUMENT_NO_ACCESS(402, "Document Can Not Access"),
    MERGE_CONFLICT(409, "Merge Conflict"),
    REPORT_NOT_FOUND(404, "Report Not Found"),
    DOC_AUTH_NOT_ACCESS(401, "문서권한에 접근할 수 없습니다.");


    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }

}


