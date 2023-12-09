package com.genesis.queryingservice.service.impl;

import com.genesis.commons.cqrs.aggregate.UserAggregate;
import com.genesis.commons.exception.ResourceNotFoundException;
import com.genesis.queryingservice.entity.User;
import com.genesis.queryingservice.mapper.UserMapper;
import com.genesis.queryingservice.repository.UserRepository;
import com.genesis.queryingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public void updateUser(UserAggregate aggregate) {
        User user = userRepository.findById(aggregate.id())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", aggregate.id()));
        userMapper.toUser(user, aggregate);
        userRepository.save(user);
    }
}
