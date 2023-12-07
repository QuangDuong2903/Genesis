package com.genesis.queryingservice.mapper;

import com.genesis.commons.cqrs.aggregate.CategoryAggregate;
import com.genesis.queryingservice.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CategoryMapper {

    Category toCategory(CategoryAggregate aggregate);

    Category toCategory(@MappingTarget Category category, CategoryAggregate aggregate);

}
