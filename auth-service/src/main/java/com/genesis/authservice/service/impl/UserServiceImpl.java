package com.genesis.authservice.service.impl;

import com.genesis.authservice.dto.request.SignUpRequest;
import com.genesis.authservice.entity.User;
import com.genesis.authservice.mapper.UserMapper;
import com.genesis.authservice.repository.UserRepository;
import com.genesis.authservice.service.UserService;
import com.genesis.commons.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public RestResponse<Void> createUser(SignUpRequest request) {
        User user = userMapper.toUser(request)
                .withPassword(passwordEncoder.encode(request.password()))
                .withBalance(BigDecimal.valueOf(1000000));
        userRepository.save(user);
        return RestResponse.created(null);
    }
}
