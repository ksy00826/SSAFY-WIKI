package com.bdos.ssafywiki.bookmark.mapper;

import com.bdos.ssafywiki.bookmark.dto.BookmarkDto;
import com.bdos.ssafywiki.bookmark.entity.Bookmark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    @Mapping(source = "bookmark.document.id", target = "docsId")
    @Mapping(source = "bookmark.document.title", target = "title")
    BookmarkDto.Detail toDetail(Bookmark bookmark);

    List<BookmarkDto.Detail> toDetailList(List<Bookmark> bookmarkList);
}
