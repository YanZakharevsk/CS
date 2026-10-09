package com.techcore.auth_service.keycloak.service.impl;

import com.techcore.auth_service.keycloak.dto.filter.UserFilter;
import com.techcore.auth_service.keycloak.dto.filter.UserSpecification;
import com.techcore.auth_service.keycloak.dto.mapper.UserMapper;
import com.techcore.auth_service.keycloak.dto.request.CreateUserRequest;
import com.techcore.auth_service.keycloak.dto.request.UpdateUserRequest;
import com.techcore.auth_service.keycloak.dto.response.PageResponse;
import com.techcore.auth_service.keycloak.dto.response.UserResponse;
import com.techcore.auth_service.keycloak.dto.response.UserShortResponse;
import com.techcore.auth_service.keycloak.entity.User;
import com.techcore.auth_service.keycloak.exception.*;
import com.techcore.auth_service.keycloak.repository.UserRepository;
import com.techcore.auth_service.keycloak.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if(userRepository.existsByEmail(request.getEmail()) == true){
            throw new EmailHasAlreadyUsedException(request.getEmail());
        }

        if(userRepository.existsByPhoneNumber(request.getPhoneNumber()) == true){
            throw new PhoneNumberHasAlreadyBookedException(request.getPhoneNumber());
        }

        User user = userMapper.toEntity(request);
        user.setIsActive(true);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return userMapper.toResponse(user);
    }

    @Override
    public PageResponse<UserResponse> getAllUsers(UserFilter userFilter, int page, int size) {
        Specification<User> specification = UserSpecification.filterBy(userFilter);

        Page<User> userPage = userRepository.findAll(
                specification,
                PageRequest.of(page, size));

        List<UserResponse> userContents = userPage.getContent()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        PageResponse<UserResponse> pageResponse = new PageResponse<>(userContents, page, size, userContents.size());

        return pageResponse;
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID userId, UpdateUserRequest request) {
        User unchangedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User changedUser = userMapper.update(unchangedUser, request);

        return userMapper.toResponse(changedUser);
    }

    @Override
    @Transactional
    public UserShortResponse activateUser(UUID userId) {
        User unchangedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if(unchangedUser.getIsActive()) throw new UserAlreadyActivateException(userId);
        unchangedUser.setIsActive(true);

        userRepository.save(unchangedUser);


        return userMapper.toShortResponse(unchangedUser);
    }

    @Override
    @Transactional
    public UserShortResponse deactivateUser(UUID userId) {
        User unchangedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if(!unchangedUser.getIsActive()) throw new UserAlreadyDeactivateException(userId);
        unchangedUser.setIsActive(false);

        userRepository.save(unchangedUser);

        return userMapper.toShortResponse(unchangedUser);
    }

    @Override
    @Transactional
    public boolean deleteUser(UUID userId) {
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(currentUser);
        return true;
    }
}
