package com.genesis.authservice.controller;

import com.genesis.authservice.dto.response.UserResponse;
import com.genesis.authservice.service.UserService;
import com.genesis.commons.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<UserResponse>> getOneUser(
            @RequestParam(required = false) boolean failure,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getOneUser(id, failure));
    }

}
