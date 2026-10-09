package com.techcore.auth_service.keycloak.dto.mapper;

import com.techcore.auth_service.keycloak.dto.request.CreateUserRequest;
import com.techcore.auth_service.keycloak.dto.request.UpdateUserRequest;
import com.techcore.auth_service.keycloak.dto.response.UserResponse;
import com.techcore.auth_service.keycloak.dto.response.UserShortResponse;
import com.techcore.auth_service.keycloak.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-02T21:42:42+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.12.1 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(CreateUserRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setName( request.getName() );
        user.setSurname( request.getSurname() );
        user.setEmail( request.getEmail() );
        user.setPhoneNumber( request.getPhoneNumber() );
        user.setBirthDate( request.getBirthDate() );

        return user;
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setUserId( user.getId() );
        userResponse.setName( user.getName() );
        userResponse.setSurname( user.getSurname() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setPhoneNumber( user.getPhoneNumber() );
        userResponse.setBirthDate( user.getBirthDate() );
        userResponse.setIsActive( user.getIsActive() );
        userResponse.setCreatedAt( user.getCreatedAt() );
        userResponse.setUpdatedAt( user.getUpdatedAt() );

        return userResponse;
    }

    @Override
    public UserShortResponse toShortResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserShortResponse userShortResponse = new UserShortResponse();

        userShortResponse.setUserId( user.getId() );
        userShortResponse.setName( user.getName() );
        userShortResponse.setSurname( user.getSurname() );
        userShortResponse.setIsActive( user.getIsActive() );

        return userShortResponse;
    }

    @Override
    public User update(User user, UpdateUserRequest request) {
        if ( request == null ) {
            return user;
        }

        user.setName( request.getName() );
        user.setSurname( request.getSurname() );
        user.setPhoneNumber( request.getPhoneNumber() );

        return user;
    }
}
