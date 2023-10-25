package com.bdos.ssafywiki.revision.mapper;

import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Revision;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RevisionMapper {

    @Mapping(source = "revision.document.id", target = "docsId")
    @Mapping(source = "revision.document.user.name", target = "author")
    @Mapping(source = "revision.document.title", target = "title")
    @Mapping(source = "revision.document.deleted", target = "deleted")
    @Mapping(source = "revision.document.createdAt", target = "createdAt")
    @Mapping(source = "revision.modifiedAt", target = "modifiedAt")
    @Mapping(source = "revision.content.text", target = "content")
    RevisionDto.Response toResponse(Revision revision);
}
