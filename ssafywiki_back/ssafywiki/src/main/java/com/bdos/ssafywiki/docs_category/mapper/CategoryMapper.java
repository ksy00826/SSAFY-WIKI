package com.bdos.ssafywiki.docs_category.mapper;

import com.bdos.ssafywiki.docs_category.dto.CategoryDto;
import com.bdos.ssafywiki.docs_category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    CategoryDto.Detail toDetail(Category category);
    List<CategoryDto.Detail> toDetailList(List<Category> categoryList);
}
