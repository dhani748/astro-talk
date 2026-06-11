package com.astrotalk.service;

import com.astrotalk.dto.RegisterUserRequest;
import com.astrotalk.dto.UpdateUserRequest;
import com.astrotalk.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse registerUser(RegisterUserRequest request);

    UserResponse getUserById(Long id);

    UserResponse getUserByEmail(String email);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    List<UserResponse> getAllUsers();
}
