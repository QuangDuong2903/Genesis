package com.genesis.queryingservice.mapper;

import com.genesis.commons.cqrs.aggregate.ProductAggregate;
import com.genesis.queryingservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = CategoryMapper.class)
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "category")
    Product toProduct(ProductAggregate aggregate);

    @Mapping(source = "categoryId", target = "category")
    void toProduct(@MappingTarget Product product, ProductAggregate aggregate);

}
