package com.genesis.commons.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;

/*
 * References: https://stackoverflow.com/a/65739712
 */
@ConditionalOnProperty(name = "spring.datasource.driver-class-name")
public class ReferenceMapper {

    @PersistenceContext
    private EntityManager entityManager;

    @ObjectFactory
    public <T> T map(@NonNull final String id, @TargetType Class<T> type) {
        return entityManager.getReference(type, id);
    }

}
