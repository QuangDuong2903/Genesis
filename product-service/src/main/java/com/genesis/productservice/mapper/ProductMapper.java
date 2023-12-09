package com.genesis.productservice.mapper;

import com.genesis.commons.cqrs.aggregate.ProductAggregate;
import com.genesis.productservice.dto.request.CreateProductRequest;
import com.genesis.productservice.dto.response.ProductResponse;
import com.genesis.productservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CategoryMapper.class)
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "category")
    Product toProduct(CreateProductRequest request);

    ProductResponse toProductResponse(Product category);

    @Mapping(source = "category.id", target = "categoryId")
    ProductAggregate toAggregate(Product product);

}
