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

    default RevisionDto.DocsResponse toResponse(Revision revision){
        List<CategoryDto.Detail> categoryList = new ArrayList<>();
        for (DocsCategory docsCategory : revision.getDocument().getCategoryList()){
            Category category = docsCategory.getCategory();
            CategoryDto.Detail detail = CategoryDto.Detail.builder()
                    .categoryId(category.getId())
                    .categoryName(category.getName()).build();
            categoryList.add(detail);
        }

        RevisionDto.DocsResponse response = RevisionDto.DocsResponse.builder()
                .categoryList(categoryList)
                .docsId(revision.getDocument().getId())
                .author(revision.getDocument().getUser().getName())
                .title(revision.getDocument().getTitle())
                .content(revision.getContent().getText())
                .createdAt(revision.getDocument().getCreatedAt())
                .modifiedAt(revision.getModifiedAt())
                .build();

        return response;
    }

    default RevisionDto.CheckUpdateResponse toCheckUpdateResponse(Revision revision, boolean canUpdate) {
        List<CategoryDto.Detail> categoryList = new ArrayList<>();
        for (DocsCategory docsCategory : revision.getDocument().getCategoryList()){
            Category category = docsCategory.getCategory();
            CategoryDto.Detail detail = CategoryDto.Detail.builder()
                    .categoryId(category.getId())
                    .categoryName(category.getName()).build();
            categoryList.add(detail);
        }
        RevisionDto.CheckUpdateResponse response = RevisionDto.CheckUpdateResponse.builder()
                .categoryList(categoryList)
                .docsId(revision.getDocument().getId())
                .title(revision.getDocument().getTitle())
                .content(revision.getContent().getText())
                .canUpdate(canUpdate)
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
