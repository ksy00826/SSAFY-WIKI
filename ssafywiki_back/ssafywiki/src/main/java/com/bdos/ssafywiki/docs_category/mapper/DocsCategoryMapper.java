package com.bdos.ssafywiki.docs_category.mapper;

import com.bdos.ssafywiki.docs_category.dto.DocsCategoryDto;
import com.bdos.ssafywiki.docs_category.entity.DocsCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface DocsCategoryMapper {
    @Mapping(source = "docsCategory.id", target = "docsCategoryId")
    @Mapping(source = "docsCategory.category.id", target = "categoryId")
    @Mapping(source = "docsCategory.category.name", target = "categoryName")
    DocsCategoryDto.DocsCategoryDetail toDocsCategoryDetail(DocsCategory docsCategory);

    List<DocsCategoryDto.DocsCategoryDetail> toDocsCategoryDetailList(List<DocsCategory> docsCategoryList);
}
