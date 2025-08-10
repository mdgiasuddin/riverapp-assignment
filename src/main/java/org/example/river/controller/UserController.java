package org.example.river.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.river.model.dto.request.UserCreateRequest;
import org.example.river.model.dto.request.UserSearchRequest;
import org.example.river.model.dto.response.UserResponse;
import org.example.river.service.base.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(CREATED)
    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/search")
    public List<UserResponse> searchUsers(UserSearchRequest request) {
        return userService.searchUsers(request);
    }
}
