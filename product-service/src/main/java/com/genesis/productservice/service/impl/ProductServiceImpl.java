package com.genesis.productservice.service.impl;

import com.genesis.commons.cqrs.channel.CQRSChannel;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.commons.messaging.Command;
import com.genesis.commons.response.RestResponse;
import com.genesis.productservice.dto.request.CreateProductRequest;
import com.genesis.productservice.dto.response.ProductResponse;
import com.genesis.productservice.entity.Product;
import com.genesis.productservice.mapper.ProductMapper;
import com.genesis.productservice.repository.ProductRepository;
import com.genesis.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final StreamBridge streamBridge;

    @Override
    public RestResponse<ProductResponse> createProduct(CreateProductRequest request) {
        Product product = productMapper.toProduct(request);
        productRepository.save(product);
        streamBridge.send(CQRSChannel.UPDATE_PRODUCT, MessageBuilder.withPayload(
                new Command<>(product.getId(), productMapper.toAggregate(product))
        ).build());
        return RestResponse.created(productMapper.toProductResponse(product));
    }

    @Override
    public void reduceQuantity(Long id, Long amount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        if (product.getQuantity() < amount)
            throw new RuntimeException("Product with id: " + id + "not enough quantity");
        product.setQuantity(product.getQuantity() - amount);
        productRepository.save(product);
        streamBridge.send(CQRSChannel.UPDATE_PRODUCT, MessageBuilder.withPayload(
                new Command<>(product.getId(), productMapper.toAggregate(product))
        ).build());
    }

    @Override
    public void compensateQuantity(Long id, Long amount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setQuantity(product.getQuantity() + amount);
        productRepository.save(product);
        streamBridge.send(CQRSChannel.UPDATE_PRODUCT, MessageBuilder.withPayload(
                new Command<>(product.getId(), productMapper.toAggregate(product))
        ).build());
    }
}
