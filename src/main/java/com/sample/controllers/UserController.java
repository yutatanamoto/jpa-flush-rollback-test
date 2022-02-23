package com.sample.controllers;

import com.sample.requests.AddUserRequest;
import com.sample.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public void save(@RequestBody AddUserRequest request) {
        userService.add(request.getFirstName(), request.getLastName());
    }
}
