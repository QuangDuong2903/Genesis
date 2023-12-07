package com.genesis.productservice.service.impl;

import com.genesis.commons.cqrs.channel.CQRSChannel;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.commons.messaging.Command;
import com.genesis.commons.response.RestResponse;
import com.genesis.productservice.dto.request.CreateCategoryRequest;
import com.genesis.productservice.dto.request.UpdateCategoryRequest;
import com.genesis.productservice.dto.response.CategoryResponse;
import com.genesis.productservice.entity.Category;
import com.genesis.productservice.mapper.CategoryMapper;
import com.genesis.productservice.repository.CategoryRepository;
import com.genesis.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final StreamBridge streamBridge;

    @Override
    public RestResponse<CategoryResponse> createCategory(CreateCategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        categoryRepository.save(category);
        streamBridge.send(CQRSChannel.CREATE_CATEGORY, new Command<>(category.getId(),
                categoryMapper.toCategoryAggregate(category)));
        return RestResponse.created(categoryMapper.toCategoryResponse(category));
    }

    @Override
    public RestResponse<CategoryResponse> updateCategory(Long id, UpdateCategoryRequest request) {
        return categoryRepository.findById(id)
                .map(category -> categoryMapper.toCategory(category, request))
                .map(categoryRepository::save)
                .map(category -> {
                    streamBridge.send(CQRSChannel.UPDATE_CATEGORY, new Command<>(category.getId(),
                            categoryMapper.toCategoryAggregate(category)));
                    return category;
                })
                .map(categoryMapper::toCategoryResponse)
                .map(RestResponse::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }
}
