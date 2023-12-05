package com.genesis.productservice.service.impl;

import com.genesis.commons.response.RestResponse;
import com.genesis.productservice.dto.request.CreateProductRequest;
import com.genesis.productservice.dto.response.ProductResponse;
import com.genesis.productservice.entity.Product;
import com.genesis.productservice.mapper.ProductMapper;
import com.genesis.productservice.repository.ProductRepository;
import com.genesis.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public RestResponse<ProductResponse> createProduct(CreateProductRequest request) {
        Product product = productMapper.toProduct(request);
        productRepository.save(product);
        return RestResponse.created(productMapper.toProductResponse(product));
    }
}
