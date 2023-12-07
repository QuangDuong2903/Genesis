package com.genesis.queryingservice.service;

import com.genesis.commons.cqrs.aggregate.CategoryAggregate;

public interface CategoryService {

    void createCategory(CategoryAggregate aggregate);

    void updateCategory(CategoryAggregate aggregate);

}
