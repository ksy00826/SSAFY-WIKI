package com.bdos.ssafywiki.discussion.mapper;

import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.discussion.entity.Discussion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface DiscussionMapper {


    Discussion toDiscussion(DiscussionDto discussionDto);

    @Mapping(source = "discussion.document.id", target = "docsId")
    @Mapping(source = "discussion.user.nickname", target = "nickname")
    @Mapping(source = "discussion.createdAt", target = "createdAt")
    @Mapping(source = "discussion.user.email", target = "email")
    DiscussionDto toDto(Discussion discussion);
}
