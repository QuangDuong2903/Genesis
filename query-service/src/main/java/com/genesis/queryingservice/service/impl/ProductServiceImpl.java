package com.genesis.queryingservice.service.impl;

import com.genesis.commons.cqrs.aggregate.ProductAggregate;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.queryingservice.entity.Product;
import com.genesis.queryingservice.mapper.ProductMapper;
import com.genesis.queryingservice.repository.ProductRepository;
import com.genesis.queryingservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public void createProduct(ProductAggregate aggregate) {
        productRepository.save(productMapper.toProduct(aggregate));
    }

    @Override
    public void updateProduct(ProductAggregate aggregate) {
        Product product = productRepository.findById(aggregate.id())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", aggregate.id()));
        productMapper.toProduct(product, aggregate);
        productRepository.save(product);
    }
}
