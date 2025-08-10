package org.example.river.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record FriendRequestSendRequest(@NotNull Long receiverId) {
}
