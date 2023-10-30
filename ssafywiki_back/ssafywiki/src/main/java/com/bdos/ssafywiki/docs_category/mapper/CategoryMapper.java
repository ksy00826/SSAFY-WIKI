package com.bdos.ssafywiki.docs_category.mapper;

import com.bdos.ssafywiki.docs_category.dto.CategoryDto;
import com.bdos.ssafywiki.docs_category.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto.Detail toDetail(Category category);
    List<CategoryDto.Detail> toDetailList(List<Category> categoryList);
}
