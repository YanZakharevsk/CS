package com.techcore.auth_service.keycloak.controller;

import com.techcore.auth_service.keycloak.dto.filter.UserFilter;
import com.techcore.auth_service.keycloak.dto.request.CreateUserRequest;
import com.techcore.auth_service.keycloak.dto.request.UpdateUserRequest;
import com.techcore.auth_service.keycloak.dto.response.PageResponse;
import com.techcore.auth_service.keycloak.dto.response.UserResponse;
import com.techcore.auth_service.keycloak.dto.response.UserShortResponse;
import com.techcore.auth_service.keycloak.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest userRequest){
        UserResponse userResponse = userService.createUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(@RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) String surname,
                                                                  @RequestParam(required = false) String phoneNumber,
                                                                  @Min(value = 0,message = "Page number can not less than 0")
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                  @Min(value = 1, message = "Size number can not less than 1")
                                                                      @Max(value = 100, message = "Size number can not more than 100")
                                                                      @RequestParam(defaultValue = "10") int size){

        UserFilter userFilter = new UserFilter(name, surname, phoneNumber);
        PageResponse<UserResponse> usersPage = userService.getAllUsers(userFilter,  page, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(usersPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "id") UUID userId){
        UserResponse userResponse = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(name = "id") UUID userId,
                                                   @Valid @RequestBody UpdateUserRequest request){

        UserResponse userResponse = userService.updateUser(userId, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userResponse);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<UserShortResponse> activateUser(@PathVariable(name = "id") UUID userId){
        UserShortResponse userShortResponse = userService.activateUser(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userShortResponse);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserShortResponse> deactivateUser(@PathVariable(name = "id") UUID userId){
        UserShortResponse userShortResponse = userService.deactivateUser(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userShortResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable(name = "id") UUID userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
