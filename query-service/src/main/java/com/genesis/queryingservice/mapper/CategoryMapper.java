package com.genesis.queryingservice.mapper;

import com.genesis.commons.cqrs.aggregate.CategoryAggregate;
import com.genesis.commons.mapper.ReferenceMapper;
import com.genesis.queryingservice.entity.Category;
import com.genesis.queryingservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = ReferenceMapper.class)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category idToCategory(Long id);

    Category toCategory(CategoryAggregate aggregate);

    void toCategory(@MappingTarget Category category, CategoryAggregate aggregate);

}
