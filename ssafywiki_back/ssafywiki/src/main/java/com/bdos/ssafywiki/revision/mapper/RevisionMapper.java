package com.bdos.ssafywiki.revision.mapper;

import com.bdos.ssafywiki.docs_category.dto.CategoryDto;
import com.bdos.ssafywiki.docs_category.entity.Category;
import com.bdos.ssafywiki.docs_category.entity.DocsCategory;
import com.bdos.ssafywiki.docs_category.mapper.CategoryMapper;
import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Revision;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RevisionMapper {

    default RevisionDto.Response toResponse(Revision revision){
        List<CategoryDto.Detail> categoryList = new ArrayList<>();
        for (DocsCategory docsCategory : revision.getDocument().getCategoryList()){
            Category category = docsCategory.getCategory();
            CategoryDto.Detail detail = CategoryDto.Detail.builder()
                    .categoryId(category.getId())
                    .categoryName(category.getName()).build();
            categoryList.add(detail);
        }

        RevisionDto.Response response = RevisionDto.Response.builder()
                .categoryList(categoryList)
                .docsId(revision.getDocument().getId())
                .author(revision.getDocument().getUser().getName())
                .title(revision.getDocument().getTitle())
                .content(revision.getContent().getText())
                .deleted(revision.getDocument().isDeleted())
                .createdAt(revision.getDocument().getCreatedAt())
                .modifiedAt(revision.getModifiedAt())
                .readAuth(revision.getDocument().getReadAuth())
                .writeAuth(revision.getDocument().getWriteAuth())
                .build();

        return response;
    }

    @Mapping(source = "revision.comment.content", target = "comment")
    @Mapping(source = "revision.origin.id", target = "originId")
    @Mapping(source = "revision.origin.number", target = "originNumber")
    RevisionDto.Version toVersion(Revision revision);

    List<RevisionDto.Version> toVersionList(List<Revision> revisionList);

    @Mapping(source = "revision.content.text", target = "content")
    RevisionDto.Detail toDetail(Revision revision);

    RevisionDto.VersionPage toVersionPage(Page<Revision> revisionPage);
}
