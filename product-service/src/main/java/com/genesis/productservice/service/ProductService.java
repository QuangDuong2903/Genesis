package com.genesis.productservice.service;

import com.genesis.commons.response.RestResponse;
import com.genesis.productservice.dto.request.CreateProductRequest;
import com.genesis.productservice.dto.response.ProductResponse;

public interface ProductService {

    RestResponse<ProductResponse> createProduct(CreateProductRequest request);

    void reduceQuantity(Long id, Long amount);

    void compensateQuantity(Long id, Long amount);

}
