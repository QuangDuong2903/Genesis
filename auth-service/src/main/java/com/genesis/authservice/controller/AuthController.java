package com.genesis.authservice.controller;

import com.genesis.authservice.dto.request.SignUpRequest;
import com.genesis.authservice.service.UserService;
import com.genesis.commons.response.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping(
            value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse<Void>> signUp(@RequestBody @Valid SignUpRequest request) {
        RestResponse<Void> response = userService.createUser(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(1).toUri();
        return ResponseEntity.created(location).body(response);
    }

}
