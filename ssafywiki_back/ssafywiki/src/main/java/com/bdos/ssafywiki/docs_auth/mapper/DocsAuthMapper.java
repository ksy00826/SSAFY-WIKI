package com.bdos.ssafywiki.docs_auth.mapper;

import com.bdos.ssafywiki.docs_auth.dto.DocsAuthDto;
import com.bdos.ssafywiki.docs_auth.entity.UserDocsAuth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocsAuthMapper {

    @Mapping(source = "userDocsAuth.user.id", target = "userId")
    @Mapping(source = "userDocsAuth.user.nickname", target = "nickname")
    @Mapping(source = "userDocsAuth.user.email", target = "email")
    @Mapping(source = "userDocsAuth.id", target = "userAuthId")
    DocsAuthDto.UserAuthResponse toUserAuth(UserDocsAuth userDocsAuth) ;
}
