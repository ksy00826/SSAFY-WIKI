package com.bdos.ssafywiki.redirect_docs.mapper;

import com.bdos.ssafywiki.redirect_docs.dto.RedirectDocsDto;
import com.bdos.ssafywiki.redirect_docs.entity.RedirectDocs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RedirectDocsMapper {
    @Mapping(source = "redirectDocs.keyword", target = "originDocsTitle")
    RedirectDocsDto.Detail toDetail(RedirectDocs redirectDocs);
}
