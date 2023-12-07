package com.genesis.queryingservice.service.impl;

import com.genesis.commons.cqrs.aggregate.CategoryAggregate;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.queryingservice.entity.Category;
import com.genesis.queryingservice.mapper.CategoryMapper;
import com.genesis.queryingservice.repository.CategoryRepository;
import com.genesis.queryingservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public void createCategory(CategoryAggregate aggregate) {
        categoryRepository.save(categoryMapper.toCategory(aggregate));
    }

    @Override
    public void updateCategory(CategoryAggregate aggregate) {
        Category category = categoryRepository.findById(aggregate.id())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", aggregate.id()));
        categoryMapper.toCategory(category, aggregate);
        categoryRepository.save(category);
    }


}
