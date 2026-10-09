package com.techcore.auth_service.keycloak.service;

import com.techcore.auth_service.keycloak.dto.filter.UserFilter;
import com.techcore.auth_service.keycloak.dto.request.CreateUserRequest;
import com.techcore.auth_service.keycloak.dto.request.UpdateUserRequest;
import com.techcore.auth_service.keycloak.dto.response.PageResponse;
import com.techcore.auth_service.keycloak.dto.response.UserResponse;
import com.techcore.auth_service.keycloak.dto.response.UserShortResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(UUID userId);

    PageResponse<UserResponse> getAllUsers(UserFilter userFilter, int page, int size);

    UserResponse updateUser(UUID userId, UpdateUserRequest request);

    UserShortResponse activateUser(UUID userId);

    UserShortResponse deactivateUser(UUID userId);



    boolean deleteUser(UUID userId);

}


