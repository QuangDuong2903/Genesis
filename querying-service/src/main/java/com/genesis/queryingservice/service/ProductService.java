package com.genesis.queryingservice.service;

import com.genesis.commons.cqrs.aggregate.ProductAggregate;

public interface ProductService {

    void updateProduct(ProductAggregate aggregate);

}
