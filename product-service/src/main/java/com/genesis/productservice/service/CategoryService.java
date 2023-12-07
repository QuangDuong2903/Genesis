package com.genesis.productservice.service;

import com.genesis.commons.response.RestResponse;
import com.genesis.productservice.dto.request.CreateCategoryRequest;
import com.genesis.productservice.dto.request.UpdateCategoryRequest;
import com.genesis.productservice.dto.response.CategoryResponse;

public interface CategoryService {

    RestResponse<CategoryResponse> createCategory(CreateCategoryRequest request);

    RestResponse<CategoryResponse> updateCategory(Long id, UpdateCategoryRequest request);

}
