package org.example.river.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.river.model.dto.request.FriendRequestSendRequest;
import org.example.river.model.dto.response.UserResponse;
import org.example.river.service.base.FriendRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/friend-requests")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @ResponseStatus(CREATED)
    @PostMapping
    public void sendFriendRequest(@Valid @RequestBody FriendRequestSendRequest request) {
        friendRequestService.sendFriendRequest(request);
    }

    @ResponseStatus(OK)
    @GetMapping("/accepted")
    public List<UserResponse> getAllFriends() {
        return friendRequestService.getAllFriends();
    }
}
