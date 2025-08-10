package org.example.river.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.river.exception.RulesViolationException;
import org.example.river.model.dto.request.FriendRequestSendRequest;
import org.example.river.model.dto.response.UserResponse;
import org.example.river.model.entity.FriendRequest;
import org.example.river.model.entity.User;
import org.example.river.repository.FriendRequestRepository;
import org.example.river.service.base.FriendRequestService;
import org.example.river.service.base.UserService;
import org.example.river.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.river.model.enumeration.FriendRequestStatus.ACCEPTED;
import static org.example.river.model.enumeration.FriendRequestStatus.DECLINED;
import static org.example.river.model.enumeration.FriendRequestStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;

    @Override
    public void sendFriendRequest(FriendRequestSendRequest request) {
        String email = SecurityUtils.getUserEmail();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        User receiver = userService.getUserById(request.receiverId());

        if (user.getId().equals(receiver.getId())) {
            throw new RulesViolationException("SELF_REQUEST_NOT_ALLOWED", "You can't send self request to other users");
        }

        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.getFriendRequest(user.getId(), receiver.getId());

        if (friendRequestOptional.isPresent()) {
            FriendRequest friendRequest = friendRequestOptional.get();
            if (friendRequest.getStatus().equals(ACCEPTED)) {
                throw new RulesViolationException("YOU_ARE_ALREADY_FRIENDS", "You are already friend");
            }
            if (friendRequest.getStatus().equals(PENDING)) {
                throw new RulesViolationException("FRIEND_REQUEST_ALREADY_SENT", "Friend request is already sent");
            }
            if (friendRequest.getStatus().equals(DECLINED)) {
                friendRequest.setStatus(PENDING);
                friendRequestRepository.save(friendRequest);
            }
        } else {
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setSender(user);
            friendRequest.setReceiver(receiver);
            friendRequest.setStatus(PENDING);
            friendRequestRepository.save(friendRequest);
        }
    }

    @Override
    public List<UserResponse> getAllFriends() {

        String email = SecurityUtils.getUserEmail();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<FriendRequest> friendRequests = friendRequestRepository.getFriendRequest(user.getId(), ACCEPTED);
        List<UserResponse> userResponses = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            if (friendRequest.getSender().equals(user)) {
                userResponses.add(new UserResponse(friendRequest.getReceiver()));
            } else {
                userResponses.add(new UserResponse(friendRequest.getSender()));
            }
        }

        return userResponses;
    }
}
