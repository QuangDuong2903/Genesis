package com.genesis.authservice.service.impl;

import com.genesis.authservice.dto.request.SignUpRequest;
import com.genesis.authservice.entity.Role;
import com.genesis.authservice.entity.User;
import com.genesis.authservice.mapper.UserMapper;
import com.genesis.authservice.repository.RoleRepository;
import com.genesis.authservice.repository.UserRepository;
import com.genesis.authservice.service.UserService;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.commons.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public RestResponse<Void> createUser(SignUpRequest request) {
        Role role = roleRepository.findOneByCode("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "code", "USER"));
        User user = userMapper.toUser(request)
                .withPassword(passwordEncoder.encode(request.password()))
                .withBalance(BigDecimal.valueOf(1000000))
                .withRoles(Set.of(role));
        userRepository.save(user);
        return RestResponse.created(null);
    }
}
