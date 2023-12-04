package com.genesis.authservice.mapper;

import com.genesis.authservice.dto.request.SignUpRequest;
import com.genesis.authservice.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toUser(SignUpRequest request);

}
