package com.bdos.ssafywiki.document.mapper;

import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Content;
import com.bdos.ssafywiki.revision.entity.Revision;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    Document postToDocument(DocumentDto.Post post);

    DocumentDto.Detail toDetail(Document document);

    DocumentDto.Recent toRecent(RevisionDto.UpdateResponse response);

    @Mapping(source = "document.id", target = "docsId")
    DocumentDto.Recent documentToRecent(Document document);
}
