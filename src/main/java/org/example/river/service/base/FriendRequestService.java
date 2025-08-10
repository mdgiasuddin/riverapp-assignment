package org.example.river.service.base;

import jakarta.validation.Valid;
import org.example.river.model.dto.request.FriendRequestSendRequest;
import org.example.river.model.dto.response.UserResponse;

import java.util.List;

public interface FriendRequestService {
    void sendFriendRequest(@Valid FriendRequestSendRequest request);

    List<UserResponse> getAllFriends();
}
