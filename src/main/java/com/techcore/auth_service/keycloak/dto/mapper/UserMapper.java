package com.techcore.auth_service.keycloak.dto.mapper;

import com.techcore.auth_service.keycloak.dto.request.CreateUserRequest;
import com.techcore.auth_service.keycloak.dto.request.UpdateUserRequest;
import com.techcore.auth_service.keycloak.dto.response.UserResponse;
import com.techcore.auth_service.keycloak.dto.response.UserShortResponse;
import com.techcore.auth_service.keycloak.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User toEntity(CreateUserRequest request);

    @Mapping(source = "id", target = "userId")
    UserResponse toResponse(User user);

    @Mapping(source = "id", target = "userId")
    UserShortResponse toShortResponse(User user);


    User update(@MappingTarget User user, UpdateUserRequest request);
}
