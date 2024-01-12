package com.genesis.queryingservice.service;

import com.genesis.commons.cqrs.aggregate.ProductAggregate;

public interface ProductService {

    void createProduct(ProductAggregate aggregate);

    void updateProduct(ProductAggregate aggregate);

}
