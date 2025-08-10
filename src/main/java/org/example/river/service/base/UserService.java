package org.example.river.service.base;

import org.example.river.model.dto.request.UserCreateRequest;
import org.example.river.model.dto.request.UserSearchRequest;
import org.example.river.model.dto.response.UserResponse;
import org.example.river.model.entity.User;

import java.util.List;

public interface UserService {
    public User getUserByEmail(String email);

    UserResponse createUser(UserCreateRequest request);

    User getUserById(long id);

    List<UserResponse> searchUsers(UserSearchRequest request);
}
