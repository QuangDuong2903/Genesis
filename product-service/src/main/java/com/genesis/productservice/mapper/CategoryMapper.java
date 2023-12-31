package com.genesis.productservice.mapper;

import com.genesis.commons.cqrs.aggregate.CategoryAggregate;
import com.genesis.commons.mapper.ReferenceMapper;
import com.genesis.productservice.dto.request.CreateCategoryRequest;
import com.genesis.productservice.dto.request.UpdateCategoryRequest;
import com.genesis.productservice.dto.response.CategoryResponse;
import com.genesis.productservice.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = ReferenceMapper.class)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category idToCategory(String id);

    Category toCategory(CreateCategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    CategoryAggregate toCategoryAggregate(Category category);

    Category toCategory(@MappingTarget Category category, UpdateCategoryRequest request);

}
