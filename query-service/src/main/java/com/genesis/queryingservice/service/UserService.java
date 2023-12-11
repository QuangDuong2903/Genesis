package com.genesis.queryingservice.service;

import com.genesis.commons.cqrs.aggregate.UserAggregate;

public interface UserService {

    void updateUser(UserAggregate aggregate);

}
