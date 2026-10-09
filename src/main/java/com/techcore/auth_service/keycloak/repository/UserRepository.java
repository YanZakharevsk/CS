package com.techcore.auth_service.keycloak.repository;

import com.techcore.auth_service.keycloak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    User getByName(String name);

    User getFirstById(UUID id);

    boolean existsByEmail(String login);

    boolean existsByPhoneNumber(String phoneNumber);

    User findByid(UUID id);

}
