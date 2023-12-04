package com.genesis.authservice.service;

import com.genesis.authservice.dto.request.SignUpRequest;
import com.genesis.commons.response.RestResponse;

public interface UserService {

    RestResponse<Void> createUser(SignUpRequest request);

}
