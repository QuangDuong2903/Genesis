package com.genesis.commons.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ReferenceMapper referenceMapper() {
        return new ReferenceMapper();
    }

}
