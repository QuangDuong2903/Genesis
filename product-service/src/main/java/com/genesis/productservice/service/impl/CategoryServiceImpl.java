package com.genesis.productservice.service.impl;

import com.genesis.commons.response.RestResponse;
import com.genesis.productservice.dto.request.CreateCategoryRequest;
import com.genesis.productservice.dto.response.CategoryResponse;
import com.genesis.productservice.entity.Category;
import com.genesis.productservice.mapper.CategoryMapper;
import com.genesis.productservice.repository.CategoryRepository;
import com.genesis.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public RestResponse<CategoryResponse> createCategory(CreateCategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        categoryRepository.save(category);
        return RestResponse.created(categoryMapper.toCategoryResponse(category));
    }
}
