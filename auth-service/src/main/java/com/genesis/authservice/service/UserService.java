package com.genesis.authservice.service;

import com.genesis.authservice.dto.request.SignUpRequest;
import com.genesis.authservice.dto.response.UserResponse;
import com.genesis.commons.response.RestResponse;

import java.math.BigDecimal;

public interface UserService {

    RestResponse<Void> createUser(SignUpRequest request);

    RestResponse<UserResponse> getOneUser(Long id, boolean failure);

    void debitBalance(Long id, BigDecimal amount);

}
