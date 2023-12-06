package com.genesis.authservice.service;

import com.genesis.authservice.dto.request.SignUpRequest;
import com.genesis.commons.response.RestResponse;

import java.math.BigDecimal;

public interface UserService {

    RestResponse<Void> createUser(SignUpRequest request);

    void debitBalance(Long id, BigDecimal amount);

}
