package com.genesis.queryingservice.mapper;

import com.genesis.commons.cqrs.aggregate.UserAggregate;
import com.genesis.commons.mapper.ReferenceMapper;
import com.genesis.queryingservice.entity.Role;
import com.genesis.queryingservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = {
        ReferenceMapper.class,
        RoleMapper.class
})
public abstract class UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    @Mapping(target = "id", ignore = true)
    abstract User idToUser(Long id);

    public abstract void toUser(@MappingTarget User user, UserAggregate aggregate);

    Set<Role> roleIdsToRoles(List<Long> ids) {
        return ids.stream().map(id -> roleMapper.idToRole(id)).collect(Collectors.toSet());
    }

}
