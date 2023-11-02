package com.bdos.ssafywiki.discussion.mapper;

import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.discussion.entity.Discussion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface DiscussionMapper {
    DiscussionMapper INSTANCE = Mappers.getMapper(DiscussionMapper.class);


    Discussion toDiscussion(DiscussionDto discussionDto);

    @Mapping(source = "discussion.document.id", target = "docsId")
    @Mapping(source = "discussion.user.nickname", target = "nickname")
    @Mapping(source = "discussion.createdAt", target = "createdAt")
    DiscussionDto toDto(Discussion discussion);
}
