package com.bdos.ssafywiki.document.mapper;

import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.revision.entity.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    Document postToDocument(DocumentDto.Post post);

    @Mapping(source = "document.id", target = "docsId")
    @Mapping(source = "document.user.id", target = "userId")
    @Mapping(source = "content.text", target = "content")
    DocumentDto.Response toResponse(Document document, Content content);
}
