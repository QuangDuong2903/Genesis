package com.genesis.queryingservice.mapper;

import com.genesis.commons.mapper.ReferenceMapper;
import com.genesis.queryingservice.entity.Role;
import com.genesis.queryingservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ReferenceMapper.class)
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    Role idToRole(Long id);

}
