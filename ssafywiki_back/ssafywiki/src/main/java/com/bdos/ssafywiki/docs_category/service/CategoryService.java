package com.bdos.ssafywiki.docs_category.service;

import com.bdos.ssafywiki.docs_category.dto.CategoryDto;
import com.bdos.ssafywiki.docs_category.entity.Category;
import com.bdos.ssafywiki.docs_category.mapper.CategoryMapper;
import com.bdos.ssafywiki.docs_category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto.Detail> readTemplateList() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryMapper.toDetailList(categoryList);
    }
}
