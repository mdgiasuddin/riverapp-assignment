package org.example.river.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.river.exception.ResourceNotFoundException;
import org.example.river.model.dto.request.UserCreateRequest;
import org.example.river.model.dto.request.UserSearchRequest;
import org.example.river.model.dto.response.UserResponse;
import org.example.river.model.entity.User;
import org.example.river.repository.UserRepository;
import org.example.river.service.base.UserService;
import org.example.river.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        String email = SecurityUtils.getUserEmail();

        User user = new User();
        user.setEmail(email);
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setCompany(request.company());
        user.setDesignation(request.designation());
        user.setPhoneNumber(request.phoneNumber());
        user.setAddress(request.address());

        return new UserResponse(userRepository.save(user));
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND_BY_ID", "No user found by id " + id));
    }

    @Override
    public List<UserResponse> searchUsers(UserSearchRequest request) {
        String email = request.getEmail() == null ? null : "%" + request.getEmail() + "%";
        String firstName = request.getFirstName() == null ? null : "%" + request.getFirstName() + "%";
        String lastName = request.getLastName() == null ? null : "%" + request.getLastName() + "%";
        String phoneNumber = request.getPhoneNumber() == null ? null : "%" + request.getPhoneNumber() + "%";
        String designation = request.getDesignation() == null ? null : "%" + request.getDesignation() + "%";
        String company = request.getCompany() == null ? null : "%" + request.getCompany() + "%";
        String address = request.getAddress() == null ? null : "%" + request.getAddress() + "%";

        List<User> users = userRepository.searchUser(email, firstName, lastName, phoneNumber, designation, company, address);

        return users.stream()
                .map(UserResponse::new)
                .toList();
    }
}
