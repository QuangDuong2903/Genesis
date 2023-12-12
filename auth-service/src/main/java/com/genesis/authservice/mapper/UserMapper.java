package com.genesis.authservice.mapper;

import com.genesis.authservice.dto.request.SignUpRequest;
import com.genesis.authservice.dto.response.UserResponse;
import com.genesis.authservice.entity.Role;
import com.genesis.authservice.entity.User;
import com.genesis.commons.cqrs.aggregate.UserAggregate;
import com.genesis.commons.persistence.BaseEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    User toUser(SignUpRequest request);

    UserAggregate toAggregate(User user);

    UserResponse toUserResponse(User user);

    default List<Long> setRolesToListIds(Set<Role> roles) {
        return roles.stream().map(BaseEntity::getId).toList();
    }

    default List<UserResponse.RoleResponse> setRolesToListRoleResponse(Set<Role> roles) {
        return roles.stream().map(role -> UserResponse.RoleResponse.builder()
                .id(role.getId().toString())
                .name(role.getName())
                .code(role.getCode())
                .build()
        ).toList();
    }

}
